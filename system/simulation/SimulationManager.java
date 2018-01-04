/**
 * 
 */
package br.ufpi.easii.athena.core.system.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufpi.easii.athena.common.exception.AthenaException;
import br.ufpi.easii.athena.common.exception.AthenaThreadExpectionHandler;
import br.ufpi.easii.athena.common.repository.TransactionManager;
import br.ufpi.easii.athena.common.util.copy.DeepCopy;
import br.ufpi.easii.athena.common.util.file.FileManageable;
import br.ufpi.easii.athena.common.util.file.FileUtil;
import br.ufpi.easii.athena.common.util.json.AbstractGraphicVOSerializer;
import br.ufpi.easii.athena.common.util.notification.NotificationManager;
import br.ufpi.easii.athena.core.component.GenericConverter;
import br.ufpi.easii.athena.core.component.GenericModule;
import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.measurement.simulation.SimulationMeasurement;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;
import br.ufpi.easii.athena.core.system.AthenaArchitecture;
import br.ufpi.easii.athena.core.system.link.Link;
import br.ufpi.easii.athena.core.system.result.Result;
import br.ufpi.easii.athena.core.system.simulation.model.Simulation;
import br.ufpi.easii.athena.core.system.simulation.model.SimulationData;
import br.ufpi.easii.athena.core.system.simulation.petri.PetriNet;
import br.ufpi.easii.athena.core.system.simulation.petri.arc.Arc;
import br.ufpi.easii.athena.core.system.simulation.petri.arc.Direction;
import br.ufpi.easii.athena.core.system.simulation.petri.place.Place;
import br.ufpi.easii.athena.core.system.simulation.petri.transition.Transition;
import br.ufpi.easii.athena.core.type.primitive.numeric.Real;
import br.ufpi.easii.athena.core.type.vo.graphic.base.AbstractGraphicVO;
import br.ufpi.easii.athena.web.model.form.configuration.ExecutionConfiguration;
import br.ufpi.easii.athena.web.model.vo.user.UserVO;
import br.ufpi.easii.athena.web.repository.SimulationDAO;

/**
 * @author Pedro Almir
 */
public class SimulationManager extends Thread{
	
	private UserVO user;
	private FileUtil fileUtil;
	private Simulation simulation;
	private SimulationDAO simulationDAO;
	
	/**
	 * @param architecture
	 * @param fileUtil
	 */
	public SimulationManager(AthenaArchitecture architecture, FileUtil fileUtil) {
		this.fileUtil = fileUtil;
		this.setUncaughtExceptionHandler(new AthenaThreadExpectionHandler());
		this.simulation = extractSimulationData("Module of Module", architecture);
	}
	
	/**
	 * @param architecture
	 * @param user
	 * @param fileUtil
	 * @param config
	 */
	public SimulationManager(AthenaArchitecture architecture, UserVO user, 
			FileUtil fileUtil, ExecutionConfiguration config) {
		this.setUncaughtExceptionHandler(new AthenaThreadExpectionHandler());
		this.simulation = extractSimulationData("", architecture);
		
		/* Check if is a synchronous execution */
		boolean sync = (config.getExecutionMode() != null && config.getExecutionMode().equals("synchronous")) ? true : false;
		this.simulation.setSync(sync);
		this.simulation.setExecutionConfiguration(config);
		
		this.user = user;
		this.fileUtil = fileUtil;
	}
	
	/**
	 * SimulationManager Constructor
	 * 
	 * @param simulationDescription
	 * @param architecture
	 * @param user
	 * @param simulationDAO
	 * @param fileUtil
	 * @param sync
	 */
	public SimulationManager(String simulationDescription, AthenaArchitecture architecture, UserVO user, 
			SimulationDAO simulationDAO, FileUtil fileUtil, ExecutionConfiguration config) {
		this.setUncaughtExceptionHandler(new AthenaThreadExpectionHandler());
		this.simulation = extractSimulationData(simulationDescription, architecture);
		
		/* Check if is a synchronous execution */
		boolean sync = (config.getExecutionMode() != null && config.getExecutionMode().equals("synchronous")) ? true : false;
		this.simulation.setSync(sync);
		this.simulation.setExecutionConfiguration(config);
		
		this.user = user;
		this.fileUtil = fileUtil;
		this.simulationDAO = simulationDAO;
	}
	
	/* ################################################################################### */
	/* ############################## MAIN EXECUTION METHOD ############################## */
	/* ################################################################################### */
	
	@Override
	public void run() {
		try {
			runSimulation();
			if(this.simulationDAO != null){
				if(this.user != null && !this.simulation.isSync()){
					Simulation saved = (Simulation) TransactionManager.save(this.simulationDAO, this.simulation);
					NotificationManager.sendExecutionNotification(this.user, saved);
				}else{
					Simulation saved = this.simulationDAO.save(this.simulation);
					this.simulation.setId(saved.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Run this.simulation
	 * @param simulationDescription
	 * @return result map
	 */
	public Map<String, Result> runSimulation() {
		/* Call execution method and get the execution delay */
		long begin = System.currentTimeMillis();
		if(this.simulation.isParallel()){
			this.runParallelSimulation();
		}else{
			this.runNonParallelSimulation();
		}
		long delay = System.currentTimeMillis() - begin;
		
		/* Check and validade the results */
		this.checkAndValidateResults();
		
		/* Process the results */
		this.processResults(delay);
		return this.simulation.getResultsSimulation();
	}
	
	/* ############################################################################ */
	/* ############################## SYNC EXECUTION ############################## */
	/* ############################################################################ */
	
	/** 
	 * Run synchronous simulation 
	 * */
	private void runNonParallelSimulation() {
		for (AthenaBundle bundle : this.simulation.getArchitecture().getRealBundles()) {
			/* Prepare this bundle to execute */
			this.prepareBundleToExecute(bundle);
			/* Get initial time */
			long beginModule = System.currentTimeMillis();
			/* Execute this bundle */
			this.executeBundle(bundle);
			/* Get execution delay */
			long delayModule = System.currentTimeMillis() - beginModule;
			/* Extract and propate the results */
			this.extractAndPropagateResults(bundle, delayModule);
		}
	}
	
	/* ############################################################################# */
	/* ############################## ASYNC EXECUTION ############################## */
	/* ############################################################################# */
	
	/**
	 * Run asynchronous simulation 
	 */
	private void runParallelSimulation(){
		/* Build the Petri network */
		PetriNet petriNet = buildPetriModel();
		/* Create a Thread pool */
		Queue<Arc> threadpool = new PriorityQueue<Arc>();
		/* Get the transitions able to fire */
		Queue<Transition> transitionsAbleToFire = new PriorityQueue<Transition>(petriNet.getTransitionsAbleToFire());
		
		do{
			/* Iterate on transitions creating threads */
			while (!transitionsAbleToFire.isEmpty()) {
				Transition transition = (Transition) transitionsAbleToFire.poll();
				for (Arc arc : transition.getArcs()) {
					arc.setUncaughtExceptionHandler(new AthenaThreadExpectionHandler());
					threadpool.add(arc); arc.start();
				}
			}
			/* Wait for the end of thread processing */
			while(!threadpool.isEmpty()){
				if(!threadpool.peek().isAlive()){
					AthenaThreadExpectionHandler handler = ((AthenaThreadExpectionHandler) threadpool.peek().getUncaughtExceptionHandler());
					if(handler != null && handler.hasError()){
						throw new AthenaException("Error in architecure execution.");
					}else{
						threadpool.poll();
					}
				}
			}
			/* Get the next transitions able to fire */
			transitionsAbleToFire.addAll(petriNet.getTransitionsAbleToFire());
		}while(!transitionsAbleToFire.isEmpty());
	}
	
	/* ######################################################################### */
	/* ############################## VALIDATIONS ############################## */
	/* ######################################################################### */
	
	
	/** Check and validate the results */
	private void checkAndValidateResults() { /* TODO: Implements it! */ }
	
	
	/* ################################################################################ */
	/* ############################## PROCESSING RESULTS ############################## */
	/* ################################################################################ */
	
	/**
	 * @param delay
	 */
	private void processResults(long delay) {
		Gson gson = new GsonBuilder().registerTypeAdapter(AbstractGraphicVO.class, new AbstractGraphicVOSerializer()).create();
		
		this.simulation.setExecutionTime(delay);
		this.simulation.addMeasurement(new SimulationMeasurement("Total Execution Time (ms)", delay, Real.valueOf((double) delay)));
		
		this.simulation.setJsonResultsSimulation(gson.toJson(this.simulation.getResultsSimulation()));
		this.simulation.setJsonMeasurements(gson.toJson(this.simulation.getMeasurements()));
	}
	
	/* ############################################################################### */
	/* ############################## AUXILIARY METHODS ############################## */
	/* ############################################################################### */
	
	/**
	 * Extract simulation data
	 * 
	 * @param architecture
	 * @param simulationDescription
	 * @return simulation
	 */
	private Simulation extractSimulationData(String simulationDescription, AthenaArchitecture architecture) {
		Map<AthenaBundle, SimulationData> info = new HashMap<AthenaBundle, SimulationData>();
		for (AthenaBundle bundle : architecture.getRealBundles()) {
			List<Input> inputsWithData = null;
			List<Output> outputsWithData = null;
			List<Setting> settingsWithData = null;
			
			if(bundle.getInputs() != null){
				inputsWithData = (List<Input>) DeepCopy.copy(bundle.getInputs(), Input.class);
				for(Input in : bundle.getInputs()){
					in.clear();
				}
			}
			if(bundle.getOutputs() != null){
				outputsWithData = (List<Output>) DeepCopy.copy(bundle.getOutputs(), Output.class);
				for(Output out : bundle.getOutputs()){
					out.clear();
				}
			}
			if(bundle.getSettings() != null){
				settingsWithData = (List<Setting>) DeepCopy.copy(bundle.getSettings(), Setting.class);
				for(Setting set : bundle.getSettings()){
					set.getType().clear();
				}
			}
			
			info.put(bundle, new SimulationData(bundle, inputsWithData, outputsWithData, settingsWithData));
		}
		return new Simulation(simulationDescription, architecture, info);
	}
	
	/**
	 * 1. Prepare bundle to execute<br>
	 * 2. Execute bundle<br>
	 * 3. Extract and propagate the results<br>
	 * @param bundle
	 */
	public void prepareExecuteExtractAndPropagateResultsOfBundle(AthenaBundle bundle){
		/* Prepare this bundle to execute */
		this.prepareBundleToExecute(bundle);
		/* Get initial time */
		long beginModule = System.currentTimeMillis();
		/* Execute this bundle */
		this.executeBundle(bundle);
		/* Get execution delay */
		long delayModule = System.currentTimeMillis() - beginModule;
		/* Extract and propate the results */
		this.extractAndPropagateResults(bundle, delayModule);
	}
	
	/**
	 * Prepare bundle to execute
	 * @param bundle
	 */
	private void prepareBundleToExecute(AthenaBundle bundle){
		/* Inject file util */
		if(bundle instanceof FileManageable){
			((FileManageable) bundle).setFileUtil(this.fileUtil);
		}
		
		this.populateInputs(bundle);
		this.populateSettings(bundle);
	}
	
	/**
	 * Execute bundle method
	 * @param bundle
	 */
	private void executeBundle(AthenaBundle bundle) {
		if(bundle instanceof GenericConverter){
			((GenericConverter) bundle).convert();
		}else if(bundle instanceof GenericModule){
			((GenericModule) bundle).load();
			Preconditions.checkArgument(((GenericModule) bundle).isLoaded(), "Problems loading the module. Make sure that the entries are correct.");
			((GenericModule) bundle).run();
		}
	}
	
	/**
	 * Extract and propagate results
	 * @param bundle
	 * @param delay
	 */
	private void extractAndPropagateResults(AthenaBundle bundle, double delay) {
		bundle.createGraphics(); bundle.createTextualReport();
		SimulationData info = findSimulationData(bundle);
		info.setOutputs(bundle.getOutputs());
		this.propagateResults(bundle);
		
		Result result = new Result(bundle.getShortName(), info);
		this.simulation.addResultSimulation(bundle.getIdentifier(), result);
		this.simulation.addMeasurement(new SimulationMeasurement(bundle.getShortName() + " Execution Time (ms)", delay, Real.valueOf(delay)));
	}
	
	/**
	 * Propagate the results
	 * @param bundle
	 * 			Source bundle
	 */
	private void propagateResults(AthenaBundle srcBundle) {
		if(srcBundle.getOutboundLinks() != null && !srcBundle.getOutboundLinks().isEmpty()){
			for (Link link : srcBundle.getOutboundLinks()) {
				link.propagate();
			}
		}
	}

	/**
	 * Populate Settings
	 * @param bundle
	 */
	private void populateSettings(AthenaBundle bundle) {
		SimulationData info = this.findSimulationData(bundle);
		Preconditions.checkNotNull(info, "Cannot find settings informations for %s module.", bundle.getName());
		if(bundle.getConfiguration().hasSettings()){
			for(Setting setting : info.getSettings()){
				bundle.addSetting(setting);
			}
		}
	}

	/**
	 * Populate bundle
	 * @param bundle
	 */
	private void populateInputs(AthenaBundle bundle) {
		SimulationData info = this.findSimulationData(bundle);
		Preconditions.checkNotNull(info, "Cannot find input informations for %s module.", bundle.getName());
		for(Input input : info.getInputs()){
			if(input.isLinked()){
				Link link = this.findLink(bundle, input);
				input.setValues(link.getDstInput().getValues());
			}
			bundle.addInput(input);
		}
	}

	/**
	 * Find simulation data
	 * 
	 * @param bundle
	 * @return simulation data
	 */
	private SimulationData findSimulationData(AthenaBundle bundle) {
		return this.simulation.getInfo().get(bundle);
	}
	
	/* ############################################################################### */
	/* ############################## PETRI NET METHODS ############################## */
	/* ############################################################################### */
	
	/**
	 * Build petri model
	 * @return petri net
	 */
	private PetriNet buildPetriModel(){
		/* Create the petri network */
		PetriNet net = new PetriNet(this.simulation.getArchitecture().getAthenaSystem().getName());
		/* First place: begin; first transition: tb; and first arc: begin -> tb */
		Place begin = net.place("begin", 1); Transition tb = net.transition("tb"); net.arc("begin -> tb", begin, tb);
		
		int transitionCount = 1;
		Queue<Place> queue = new PriorityQueue<Place>();
		HashSet<String> uniqueBundles = new HashSet<String>();
		ArrayList<Transition> disconnectedTransition = new ArrayList<Transition>();
		List<AthenaBundle> initialBundles = SimulationManager.getInitialBundles(this.simulation.getArchitecture());
		
		for (AthenaBundle bundle : initialBundles) {
			Place place = net.place(this, bundle);
			queue.add(place);
			/* Building an arc: tb -> [...] */
			net.arc("tb -> " + bundle.getIdentifier(), tb, place);
		}
		
		while(!queue.isEmpty()){
			boolean disconnected = true;
			Place actualPlace = queue.poll();
			AthenaBundle bundle = actualPlace.getBundle();
			if(!uniqueBundles.contains(bundle.getIdentifier())){
				List<AthenaBundle> nextList = getNextModules(bundle);
				uniqueBundles.add(bundle.getIdentifier());
				
				/* Creating an arc between actualPlace and the new transition */
				Transition transition = net.transition("t" + (transitionCount++));
				net.arc(actualPlace.getName() + " -> " + transition.getName(), actualPlace, transition);
				
				for (AthenaBundle child : nextList) {
					Place tmp = new Place(this, child);
					if(!queue.contains(tmp)){
						/* Creating a new place and an arc between the new transition and placeChild */
						Place placeChild = net.place(this, child);
						net.arc(transition.getName() + " -> " + placeChild.getName(), transition, placeChild);
						/* Adding new child to queue */
						queue.add(placeChild);
						disconnected = false;
					}else{
						Transition t = findTransitionToPlace(net, tmp);
						net.arc(actualPlace.getName() + " -> " + t.getName(), actualPlace, t);
					}
				}
				/* get the disconnected transitions */
				if(disconnected){
					disconnectedTransition.add(transition);
				}
			}
		}
		
		/* Connect the disconnected transitions to the end place */
		Place end = net.place("end");
		for(Transition t : disconnectedTransition){
			net.arc(t.getName() + " -> " + end.getName(), t, end);
		}
		
		return net;
	}
	
	/**
	 * Find transition to place
	 * @param net
	 * @param tmp
	 * @return
	 */
	private Transition findTransitionToPlace(PetriNet net, Place tmp) {
		for(Arc arc : net.getArcs()){
			if(arc.getDirection().equals(Direction.TRANSITION_TO_PLACE) && arc.getPlace().equals(tmp)){
				return arc.getTransition();
			}
		}
		return null;
	}

	/**
	 * Get next modules
	 * @param bundle
	 * @return list with the next module from this bundle.
	 */
	private List<AthenaBundle> getNextModules(AthenaBundle bundle) {
		Set<AthenaBundle> nextSet = new LinkedHashSet<AthenaBundle>();
		for(Link l : bundle.getOutboundLinks()){
			nextSet.add(l.getDstModule());
		}
		/* Check indirect dependency */
		ArrayList<AthenaBundle> tmp = new ArrayList<AthenaBundle>(nextSet);
		ArrayList<AthenaBundle> result = new ArrayList<AthenaBundle>();
		
		for(AthenaBundle b1 : tmp){
			boolean hasDependency = false;
			for(AthenaBundle b2 : tmp){
				for(Link l1 : b1.getInboundLinks()){
					for(Link l2 : b2.getOutboundLinks()){
						if(l1.equals(l2)){
							hasDependency = true;
							break;
						}
					}
					if(hasDependency) break;
				}
				if(hasDependency) break;
			}
			if(!hasDependency){
				result.add(b1);
			}
		}
		return result;
	}

	/**
	 * Get Initial bundles
	 * 
	 * @param architecture
	 * @return list with initial bundles
	 */
	public static List<AthenaBundle> getInitialBundles(AthenaArchitecture architecture) {
		ArrayList<AthenaBundle> list = new ArrayList<AthenaBundle>();
		Iterator<AthenaBundle> iterator = architecture.getRealBundles().iterator();
		while (iterator.hasNext()) {
			AthenaBundle athenaBundle = (AthenaBundle) iterator.next();
			boolean allInputsDisconnected = true;
			for(Input in : athenaBundle.getInputs()){
				allInputsDisconnected &= !in.isLinked(); 
			}
			if(allInputsDisconnected){
				list.add(athenaBundle);
			}
		}
		return list;
	}
	
	/**
	 * Get Final bundles
	 * 
	 * @param architecture
	 * @return list with final bundles
	 */
	public static List<AthenaBundle> getFinalBundles(AthenaArchitecture architecture){
		ArrayList<AthenaBundle> list = new ArrayList<AthenaBundle>();
		Iterator<AthenaBundle> iterator = architecture.getRealBundles().iterator();
		while (iterator.hasNext()) {
			AthenaBundle athenaBundle = (AthenaBundle) iterator.next();
			boolean allOutputsDisconnected = true;
			for(Output out : athenaBundle.getOutputs()){
				allOutputsDisconnected &= !out.isLinked(); 
			}
			if(allOutputsDisconnected){
				list.add(athenaBundle);
			}
		}
		return list;
	}
	
	/**
	 * Find link by destination input
	 * @param dstInput
	 * @return system link or <code>null</code> if not found link with this id.
	 */
	private Link findLink(AthenaBundle bundle, Input dstInput){
		for(Link l : bundle.getInboundLinks()){
			/* Check: identifier, name and representation */
			if(l.getDstInput().equals(dstInput)){
				return l;
			}
		}
		return null;
	}
	
	/* ####################################################################################### */
	/* ############################## GETTER AND SETTER METHODS ############################## */
	/* ####################################################################################### */

	/**
	 * @return the simulation
	 */
	public Simulation getSimulation() {
		return simulation;
	}

	/**
	 * @return the fileUtil
	 */
	public FileUtil getFileUtil() {
		return fileUtil;
	}

	/**
	 * @param fileUtil the fileUtil to set
	 */
	public void setFileUtil(FileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}
}
