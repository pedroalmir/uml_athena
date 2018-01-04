/**
 * 
 */
package br.ufpi.easii.athena.core.system.simulation.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.ufpi.easii.athena.common.env.AthenaEnvironment;
import br.ufpi.easii.athena.common.model.GenericEntity;
import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.measurement.simulation.SimulationMeasurement;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;
import br.ufpi.easii.athena.core.system.AthenaArchitecture;
import br.ufpi.easii.athena.core.system.result.Result;
import br.ufpi.easii.athena.web.model.form.configuration.ExecutionConfiguration;

/**
 * Sequence of actions to create a computer system and run your simulation:
 * <br><br>
 * 1. Define modules and their connections;<br>
 * 2. Inform the input data and possible settings;<br>
 * 3. Trigger the execution of the simulation;<br>
 * 4. Analyze the results;<br>
 * <br><br>
 * 
 * @author Pedro Almir
 *
 */
@Entity
@Table(name = "simulation")
public class Simulation extends GenericEntity {
	/** Serial Version UID */
	private static final long serialVersionUID = 383391393330882188L;
	
	@Column(columnDefinition="LONGTEXT")
	private String description;
	private Long executionTime;
	private boolean sync, parallel;
	
	/** Execution configuration */
	private String executionMode;
	private String serviceProvider;
	private Integer memory;
	private Integer processor;
	
	/** Simulation Data */
	@Transient private Map<AthenaBundle, SimulationData> info;

	@Temporal(TemporalType.TIMESTAMP)
	private Date executionDate;
	
	/**
	 * Results simulation
	 * Key: frontIdentifier
	 * Value: results ()
	 * */
	@Transient
	private Map<String, Result> resultsSimulation;
	@Column(columnDefinition = "LONGTEXT")
	private String jsonResultsSimulation;
	
	@Transient
	private List<SimulationMeasurement> measurements;
	@Column(columnDefinition = "LONGTEXT")
	private String jsonMeasurements;
	
	@ManyToOne(targetEntity = AthenaArchitecture.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private AthenaArchitecture architecture;
	
	/**
	 * Default constructor
	 */
	public Simulation() {
		this.sync = AthenaEnvironment.SYNC;
		this.parallel = AthenaEnvironment.PARALLEL;
		
		this.executionDate = new Date();
		this.info = new HashMap<AthenaBundle, SimulationData>();
		this.measurements = new ArrayList<SimulationMeasurement>();
		this.resultsSimulation = new LinkedHashMap<String, Result>();
	}
	
	/**
	 * @param description
	 * @param architecture
	 */
	public Simulation(String description, AthenaArchitecture architecture) {
		this.sync = AthenaEnvironment.SYNC;
		this.parallel = AthenaEnvironment.PARALLEL;
		
		this.description = description;
		this.architecture = architecture;
		this.executionDate = new Date();
		this.info = new HashMap<AthenaBundle, SimulationData>();
		this.measurements = new ArrayList<SimulationMeasurement>();
		this.resultsSimulation = new LinkedHashMap<String, Result>();
	}
	
	/**
	 * @param description
	 * @param architecture
	 * @param info
	 */
	public Simulation(String description, AthenaArchitecture architecture, Map<AthenaBundle, SimulationData> info) {
		this.sync = AthenaEnvironment.SYNC;
		this.parallel = AthenaEnvironment.PARALLEL;
		
		this.description = description;
		this.architecture = architecture;
		
		this.info = info;
		this.executionDate = new Date();
		this.measurements = new ArrayList<SimulationMeasurement>();
		this.resultsSimulation = new LinkedHashMap<String, Result>();
	}
	
	/**
	 * @param description
	 * @param architecture
	 * @param info
	 * @param executionDate
	 */
	public Simulation(String description, AthenaArchitecture architecture, Map<AthenaBundle, SimulationData> info, Date executionDate) {
		this.sync = AthenaEnvironment.SYNC;
		this.parallel = AthenaEnvironment.PARALLEL;
		
		this.description = description;
		this.architecture = architecture;
		
		this.info = info;
		this.executionDate = executionDate;
		this.measurements = new ArrayList<SimulationMeasurement>();
		this.resultsSimulation = new LinkedHashMap<String, Result>();
	}
	
	/**
	 * @param bundle
	 * @param inputs
	 * @param outputs
	 * @param settings
	 */
	public void addSimulationData(AthenaBundle bundle, List<Input> inputs, List<Output> outputs, List<Setting> settings){
		this.info.put(bundle, new SimulationData(bundle, inputs, outputs, settings));
	}
	
	/**
	 * @param bundle
	 * @param inputs
	 * @param outputs
	 * @param settings
	 */
	public void addSimulationData(AthenaBundle bundle){
		this.info.put(bundle, separateProcedureOfData(bundle));
	}
	
	/**
	 * @param bundle
	 * @return simulation data
	 */
	private SimulationData separateProcedureOfData(AthenaBundle bundle){
		return null;
	}
	
	/**
	 * @param identifier
	 * @param result
	 */
	public void addResultSimulation(String identifier, Result result){
		if(identifier != null && !identifier.isEmpty() && result != null){
			this.resultsSimulation.put(identifier, result);
		}
	}
	
	/**
	 * @param measurement
	 */
	public void addMeasurement(SimulationMeasurement measurement){
		if(this.measurements != null){
			this.measurements.add(measurement);
		}
	}
	
	/**
	 * @param config
	 */
	public void setExecutionConfiguration(ExecutionConfiguration config){
		this.memory = config.getMemory();
		this.processor = config.getProcessor();
		this.executionMode = config.getExecutionMode();
		this.serviceProvider = config.getServiceProvider();
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the info
	 */
	public Map<AthenaBundle, SimulationData> getInfo() {
		return info;
	}

	/**
	 * @return the executionDate
	 */
	public Date getExecutionDate() {
		return executionDate;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((architecture == null) ? 0 : architecture.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Simulation other = (Simulation) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (architecture == null) {
			if (other.architecture != null)
				return false;
		} else if (!architecture.equals(other.architecture))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Simulation [description=" + description + ", architecture=" + architecture + ", executionDate=" + executionDate + "]";
	}

	/**
	 * @return the resultsSimulation
	 */
	public Map<String, Result> getResultsSimulation() {
		return resultsSimulation;
	}

	/**
	 * @param resultsSimulation the resultsSimulation to set
	 */
	public void setResultsSimulation(Map<String, Result> resultsSimulation) {
		this.resultsSimulation = resultsSimulation;
	}

	/**
	 * @return the jsonResultsSimulation
	 */
	public String getJsonResultsSimulation() {
		return jsonResultsSimulation;
	}

	/**
	 * @param jsonResultsSimulation the jsonResultsSimulation to set
	 */
	public void setJsonResultsSimulation(String jsonResultsSimulation) {
		this.jsonResultsSimulation = jsonResultsSimulation;
	}

	/**
	 * @return the architecture
	 */
	public AthenaArchitecture getArchitecture() {
		return architecture;
	}

	/**
	 * @param architecture the architecture to set
	 */
	public void setArchitecture(AthenaArchitecture architecture) {
		this.architecture = architecture;
	}

	/**
	 * @return the executionTime
	 */
	public Long getExecutionTime() {
		return executionTime;
	}

	/**
	 * @param executionTime the executionTime to set
	 */
	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * @return the measurements
	 */
	public List<SimulationMeasurement> getMeasurements() {
		return measurements;
	}

	/**
	 * @param measurements the measurements to set
	 */
	public void setMeasurements(List<SimulationMeasurement> measurements) {
		this.measurements = measurements;
	}

	/**
	 * @return the jsonMeasurements
	 */
	public String getJsonMeasurements() {
		return jsonMeasurements;
	}

	/**
	 * @param jsonMeasurements the jsonMeasurements to set
	 */
	public void setJsonMeasurements(String jsonMeasurements) {
		this.jsonMeasurements = jsonMeasurements;
	}

	/**
	 * Load this.resultsSimulation
	 */
	public void loadResultsSimulation() {
		if(this.jsonResultsSimulation != null && !this.jsonResultsSimulation.isEmpty()){
			this.resultsSimulation = new Gson().fromJson(this.jsonResultsSimulation, new TypeToken<Map<String, Result>>(){}.getType());
		}
	}

	/**
	 * @return the sync
	 */
	public boolean isSync() {
		return sync;
	}

	/**
	 * @param sync the sync to set
	 */
	public void setSync(boolean sync) {
		this.sync = sync;
	}

	/**
	 * @return the parallel
	 */
	public boolean isParallel() {
		return parallel;
	}

	/**
	 * @param parallel the parallel to set
	 */
	public void setParallel(boolean parallel) {
		this.parallel = parallel;
	}

	/**
	 * @return the executionMode
	 */
	public String getExecutionMode() {
		return executionMode;
	}

	/**
	 * @param executionMode the executionMode to set
	 */
	public void setExecutionMode(String executionMode) {
		this.executionMode = executionMode;
	}

	/**
	 * @return the serviceProvider
	 */
	public String getServiceProvider() {
		return serviceProvider;
	}

	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	/**
	 * @return the memory
	 */
	public Integer getMemory() {
		return memory;
	}

	/**
	 * @param memory the memory to set
	 */
	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	/**
	 * @return the processor
	 */
	public Integer getProcessor() {
		return processor;
	}

	/**
	 * @param processor the processor to set
	 */
	public void setProcessor(Integer processor) {
		this.processor = processor;
	}
}
