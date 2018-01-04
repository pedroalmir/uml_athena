/**
 * 
 */
package br.ufpi.easii.athena.core.component.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.ufpi.easii.athena.common.model.enums.AthenaBundleGroup;
import br.ufpi.easii.athena.common.util.factory.SystemFactory;
import br.ufpi.easii.athena.common.util.file.FileManageable;
import br.ufpi.easii.athena.common.util.file.FileUtil;
import br.ufpi.easii.athena.common.util.repository.JPAUtil;
import br.ufpi.easii.athena.core.algorithm.Algorithm;
import br.ufpi.easii.athena.core.component.GenericModule;
import br.ufpi.easii.athena.core.component.base.AbstractBundle;
import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.component.configuration.Configuration;
import br.ufpi.easii.athena.core.component.configuration.NewAbstractionConfiguration;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.config.PutConfiguration;
import br.ufpi.easii.athena.core.put.setting.Setting;
import br.ufpi.easii.athena.core.solution.Solution;
import br.ufpi.easii.athena.core.system.AthenaArchitecture;
import br.ufpi.easii.athena.core.system.link.Link;
import br.ufpi.easii.athena.core.system.simulation.SimulationManager;
import br.ufpi.easii.athena.core.type.base.Type;
import br.ufpi.easii.athena.web.model.form.bundle.AthenaBundleForm;
import br.ufpi.easii.athena.web.model.form.link.LinkForm;
import br.ufpi.easii.athena.web.repository.AthenaArchitectureDAO;

/**
 * @author PedroAlmir
 */
public class ModuleOfModules extends AbstractBundle implements GenericModule, FileManageable{
	
	/** Basic fields */
	private String key;
	private String name;
	private String shortName;
	private String description;
	private AthenaBundleGroup group;
	private String imagePath;
	
	private AthenaArchitecture architecture;
	private FileUtil fileUtil;
	
	/**
	 * Default constructor
	 */
	public ModuleOfModules() {
		super("architecture_bundle");
	}

	/**
	 * @param identifier
	 */
	public ModuleOfModules(String identifier) {
		super(identifier);
	}
	
	/**
	 * Load architecture
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public void loadArchitecture(HttpServletRequest request) throws InstantiationException, IllegalAccessException{
		EntityManager entityManager = JPAUtil.getEntityManager();
		AthenaArchitectureDAO dao = new AthenaArchitectureDAO();
		dao.setEntityManager(entityManager);
		
		Gson gson = new Gson();
		this.architecture = dao.getArchitectureByAccessKey(this.key);
		if(this.architecture != null && (this.architecture.getJsonBundles() != null || this.architecture.getJsonLinks() != null)){
			java.lang.reflect.Type typeListBundle = new TypeToken<ArrayList<AthenaBundleForm>>(){}.getType();
			this.architecture.setBundles((List<AthenaBundleForm>) gson.fromJson(this.architecture.getJsonBundles(), typeListBundle)); 
			
			java.lang.reflect.Type typeListLink = new TypeToken<ArrayList<LinkForm>>(){}.getType();
			this.architecture.setLinks((List<LinkForm>) gson.fromJson(this.architecture.getJsonLinks(), typeListLink));;
			SystemFactory.createRealBundlesAndLinksInArchitecture(this.architecture, request);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.base.AthenaBundle#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.base.AthenaBundle#getShortName()
	 */
	@Override
	public String getShortName() {
		return this.shortName;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.base.AthenaBundle#getGroup()
	 */
	@Override
	public AthenaBundleGroup getGroup() {
		return this.group;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.base.AthenaBundle#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.base.AthenaBundle#getImagePath()
	 */
	@Override
	public String getImagePath() {
		return this.imagePath;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.base.AthenaBundle#getConfiguration()
	 */
	@Override
	public Configuration getConfiguration() {
		if(this.architecture != null){
			List<AthenaBundle> initialBundles = SimulationManager.getInitialBundles(architecture);
			List<AthenaBundle> finalBundles = SimulationManager.getFinalBundles(architecture);
			
			if(initialBundles.size() == 1 && finalBundles.size() == 1){
				/* Input configuration */
				AthenaBundle firstBundle = initialBundles.get(0);
				PutConfiguration inputConfiguration = firstBundle.getConfiguration().getInputConfiguration();
				inputConfiguration.setMinimum(firstBundle.getInputs().size()); inputConfiguration.setMaximum(firstBundle.getInputs().size());
				ArrayList<Type> types = new ArrayList<Type>();
				LinkedHashSet<String> names = new LinkedHashSet<String>();
				for(Input in : firstBundle.getInputs()){
					names.add(in.getName());
					types.add(in.getType());
				}
				inputConfiguration.createDefaultNameList(names);
				inputConfiguration.setAvailableTypes(types);
				
				/* Output configuration */
				AthenaBundle lastBundle = finalBundles.get(0);
				PutConfiguration outputConfiguration = lastBundle.getConfiguration().getOutputConfiguration();
				outputConfiguration.setMinimum(lastBundle.getOutputs().size()); outputConfiguration.setMaximum(lastBundle.getOutputs().size());
				types = new ArrayList<Type>();
				names = new LinkedHashSet<String>();
				for(Output out : lastBundle.getOutputs()){
					names.add(out.getName());
					types.add(out.getType());
				}
				outputConfiguration.createDefaultNameList(names);
				outputConfiguration.setAvailableTypes(types);
				
				/* Settings */
				ArrayList<Setting> settings = new ArrayList<Setting>();
				for(AthenaBundle bundle : architecture.getRealBundles()){
					if(bundle.getConfiguration().hasSettings() && bundle.getConfiguration().getSettings() != null){
						for(Setting s : bundle.getConfiguration().getSettings()){
							s.setIdentifier(bundle.getIdentifier() + "&_&" + s.getIdentifier());
							settings.add(s);
						}
					}
				}
				
				/* Bundle configuration */
				return new NewAbstractionConfiguration(inputConfiguration, outputConfiguration, settings);
			}
		}
		return null;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(AthenaBundleGroup group) {
		this.group = group;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public Algorithm getAlgorithm() {
		return null;
	}

	@Override
	public boolean isOptimizationAlgorithm() {
		return false;
	}

	@Override
	public void load(List<Input> inputs, List<Output> outputs, List<Setting> settings) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.settings = settings;
		super.loaded = true;
	}

	@Override
	public void load() {
		super.loaded = true;
	}

	@Override
	public boolean isPublic() {
		return true;
	}

	/**
	 * 1. Get JPAUtil and create EntityManager (loadArchitecture())
	 * 2. Find architecture (loadArchitecture())
	 * 3. Get first module and inject inputs
	 * 4. Get settings and inject on modules
	 * 5. Create SimulationManager
	 * 6. run!
	 * 7. Get last module and extract outputs
	 */
	@Override
	public List<Solution> run() {
		if(this.architecture != null){
			HashMap<String,AthenaBundle> map = loadBundlesInMap(architecture);
			List<AthenaBundle> initialBundles = SimulationManager.getInitialBundles(architecture);
			List<AthenaBundle> finalBundles = SimulationManager.getFinalBundles(architecture);
			
			if(initialBundles.size() == 1 && finalBundles.size() == 1){
				AthenaBundle first = initialBundles.get(0);
				for(Input in : this.inputs){
					in.setLinked(false);
				}
				first.getInputs().clear(); 
				first.getInputs().addAll(this.inputs);
				
				for(Setting setting : this.settings){
					String[] split = setting.getIdentifier().split("&_&", 2);
					setting.setIdentifier(split[1]);
					map.get(split[0]).getSettings().add(setting);
				}
				
				SimulationManager manager = new SimulationManager(architecture, this.fileUtil);
				manager.run();
				
				this.outputs = finalBundles.get(0).getOutputs();
				for(Link link : this.getOutboundLinks()){
					for(Output out : this.outputs){
						if(link.getSrcOutput().getName().equals(out.getName())){
							link.getSrcOutput().addValues(out.getValues());
							break;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Load bundles in map
	 * @param architecture
	 * @return map
	 */
	private HashMap<String, AthenaBundle> loadBundlesInMap(AthenaArchitecture architecture){
		HashMap<String, AthenaBundle> map = new LinkedHashMap<String, AthenaBundle>();
		for(AthenaBundle bundle : architecture.getRealBundles()){
			bundle.getSettings().clear();
			map.put(bundle.getIdentifier(), bundle);
		}
		return map;
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
