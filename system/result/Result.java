/**
 * 
 */
package br.ufpi.easii.athena.core.system.result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufpi.easii.athena.core.measurement.base.Measurement;
import br.ufpi.easii.athena.core.measurement.simulation.SimulationMeasurement;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.system.simulation.model.SimulationData;
import br.ufpi.easii.athena.core.system.simulation.model.SimulationDataVO;
import br.ufpi.easii.athena.core.type.container.graphic.base.AbstractGraphic;
import br.ufpi.easii.athena.core.type.vo.base.TypeVO;
import br.ufpi.easii.athena.core.type.vo.graphic.base.AbstractGraphicVO;
import br.ufpi.easii.athena.web.model.vo.put.InputVO;
import br.ufpi.easii.athena.web.model.vo.put.OutputVO;
import br.ufpi.easii.athena.web.model.vo.put.SettingVO;

/**
 * @author Ronyerison
 *
 */
public class Result {

	private String bundleName;
	/** List of simulation data */
	private SimulationDataVO simulationData;
	/** List of measurements */
	private List<SimulationMeasurement> measurements;
	/** List of outputs */
	private List<OutputVO> solutions;
	/** List of graphics */
	private List<AbstractGraphicVO> graphics;
	/** Textual report */
	private String textualReport;
	
	/**
	 * Default constructor
	 */
	public Result() {
		this.measurements = new LinkedList<SimulationMeasurement>();
		this.solutions = new LinkedList<OutputVO>();
		this.graphics = new ArrayList<AbstractGraphicVO>();
	}
	
	
	/**
	 * @param bundleName
	 * @param simulationData
	 * @param measurements
	 */
	public Result(String bundleName, SimulationData simulationData, List<SimulationMeasurement> measurements) {
		this.bundleName = bundleName;
		this.measurements = measurements;
		this.simulationData = new SimulationDataVO(simulationData);
		if(simulationData.getBundle().getTextualReport() != null){
			this.textualReport = simulationData.getBundle().getTextualReport().trim();
		}
		
		/* Solutions */
		this.solutions = new LinkedList<OutputVO>();
		for(Output out : simulationData.getOutputs()){
			this.solutions.add(new OutputVO(out));
		}
		
		/* Graphics */
		this.graphics = new ArrayList<AbstractGraphicVO>();
		for (AbstractGraphic graphic : simulationData.getBundle().getGraphics()) {
			this.graphics.add(graphic.getTypeVO());
		}
	}
	
	
	/**
	 * @param bundleName
	 * @param simulationData
	 */
	public Result(String bundleName, SimulationData simulationData) {
		this.bundleName = bundleName;
		this.simulationData = new SimulationDataVO(simulationData);
		if(simulationData.getBundle().getTextualReport() != null){
			this.textualReport = simulationData.getBundle().getTextualReport().trim();
		}
		
		/* Solutions */
		this.solutions = new LinkedList<OutputVO>();
		for(Output out : simulationData.getOutputs()){
			this.solutions.add(new OutputVO(out));
		}
		
		/* Measurements */
		this.measurements = new LinkedList<SimulationMeasurement>();
		for(Measurement measurement : simulationData.getBundle().getMeasurements()){
			this.measurements.add(new SimulationMeasurement(measurement));
		}
		
		/* Graphics */
		this.graphics = new ArrayList<AbstractGraphicVO>();
		for (AbstractGraphic graphic : simulationData.getBundle().getGraphics()) {
			this.graphics.add(graphic.getTypeVO());
		}
	}

	/**
	 * @param measurement
	 */
	public void addMeasurement(SimulationMeasurement measurement){
		this.measurements.add(measurement);
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Result [solutions=" + simulationData + ", measurements=" + measurements + "]";
	}
	
	public String toStringPlus(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("##################################################################\n");
		buffer.append("Module: " + this.bundleName + "\n");
		buffer.append("Settings: " + ((this.simulationData.getSettings().size() == 0) ? "Empty\n" : "\n"));
		for(SettingVO setting : this.simulationData.getSettings()){
			buffer.append("\t" + setting.getName() + ": " + setting.getType().getValue() + "\n");	
		}
		
		buffer.append("Inputs:\n");
		for(InputVO input : this.simulationData.getInputs()){
			buffer.append("\t" + input.getName() + ": [");
			StringBuffer inputs = new StringBuffer(); int size = 0;
			for(TypeVO type : input.getComponents()){
				inputs.append(type.getValue() + ((size == input.getComponents().size() - 1) ? "" : ", "));
				size++;
			}
			buffer.append(inputs.toString() + "]\n");
		}
		
		buffer.append("Results:\n");
		for(OutputVO output : this.solutions){
			buffer.append("\t" + output.getName() + ": [");
			StringBuffer outputs = new StringBuffer(); int size = 0;
			for(TypeVO type : output.getComponents()){
				outputs.append(type.getValue() + ((size == output.getComponents().size() - 1) ? "" : ", "));
				size++;
			}
			buffer.append(outputs.toString() + "]\n");
		}
		
		buffer.append("##################################################################\n");
		return buffer.toString();
	} 

	/**
	 * @return the simulationData
	 */
	public SimulationDataVO getSimulationData() {
		return simulationData;
	}

	/**
	 * @param simulationData the simulationData to set
	 */
	public void setSimulationData(SimulationDataVO simulationData) {
		this.simulationData = simulationData;
	}

	/**
	 * @return the solutions
	 */
	public List<OutputVO> getSolutions() {
		return solutions;
	}

	/**
	 * @param solutions the solutions to set
	 */
	public void setSolutions(List<OutputVO> solutions) {
		this.solutions = solutions;
	}

	/**
	 * @return the bundleName
	 */
	public String getBundleName() {
		return bundleName;
	}

	/**
	 * @param bundleName the bundleName to set
	 */
	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}


	/**
	 * @return the textualReport
	 */
	public String getTextualReport() {
		return textualReport;
	}


	/**
	 * @param textualReport the textualReport to set
	 */
	public void setTextualReport(String textualReport) {
		this.textualReport = textualReport;
	}


	/**
	 * @return the graphics
	 */
	public List<AbstractGraphicVO> getGraphics() {
		return graphics;
	}


	/**
	 * @param graphics the graphics to set
	 */
	public void setGraphics(List<AbstractGraphicVO> graphics) {
		this.graphics = graphics;
	}
}
