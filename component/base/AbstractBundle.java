/**
 * 
 */
package br.ufpi.easii.athena.core.component.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Preconditions;

import br.ufpi.easii.athena.core.measurement.base.Measurement;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;
import br.ufpi.easii.athena.core.system.link.Link;
import br.ufpi.easii.athena.core.type.base.Type;
import br.ufpi.easii.athena.core.type.container.graphic.base.AbstractGraphic;

/**
 * @author Pedro Almir
 */
public abstract class AbstractBundle implements AthenaBundle {
	
	/** This field must be unique */
	private String identifier;
	/** List of inputs */
	protected List<Input> inputs;
	/** List of outputs */
	protected List<Output> outputs;
	/** List of settings */
	protected List<Setting> settings;
	/** Is loaded? */
	protected boolean loaded;
	
	/** Inbounds links */
	protected List<Link> inboundLinks;
	/** Outbounds links */
	protected List<Link> outboundLinks;
	
	/** Measurements */
	protected List<Measurement> measurements;
	/** Graphics */
	protected List<AbstractGraphic> graphics;
	/** Textual report */
	protected String textualReport;
	
	/**
	 * @param identifier
	 */
	public AbstractBundle(String identifier) {
		this.identifier = identifier;
		/* puts */
		this.inputs = new LinkedList<Input>();
		this.outputs = new LinkedList<Output>();
		this.settings = new LinkedList<Setting>();
		/* links */
		this.inboundLinks = new LinkedList<Link>();
		this.outboundLinks = new LinkedList<Link>();
		/* Measurement */
		this.measurements = new LinkedList<Measurement>();
		/* Graphics */
		this.graphics = new ArrayList<AbstractGraphic>();
	}
	
	@Override
	public String getFileDescriptionPath() {
		return null;
	}
	
	public Setting findSetting(String identifier) {
		if(this.getConfiguration().hasSettings()){
			for(Setting s : this.getSettings()){
				if(s.getIdentifier().equals(identifier)){
					return s;
				}
			}
		}
		return null;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * @param allLinks
	 */
	public void populateInAndOutboundLinks(List<Link> allLinks){
		for (Link link : allLinks) {
			if(link.getSrcModule().equals(this)){
				this.outboundLinks.add(link);
			}else if(link.getDstModule().equals(this)){
				this.inboundLinks.add(link);
			}
		}
	}
	
	public List<Measurement> getMeasurements(){
		return this.measurements;
	}
	
	public void setMeasurements(List<Measurement> measurements){
		this.measurements = measurements;
	}
	
	public void addMeasurement(Measurement measurement){
		if(this.measurements != null){
			this.measurements.add(measurement);
		}
	}

	/*********************************************************************************************************/
	/*********************************************      Input      *******************************************/
	/*********************************************************************************************************/
	
	public void addInput(Input input) {
		boolean ok = false;
		for(Type t : this.getConfiguration().getInputConfiguration().getAvailableTypes()){
			if(input.getType().getClass().equals(t.getClass())){
				ok = true;
				break;
			}
		}
		if(!ok){
			throw new IllegalArgumentException("Mismatched types.");
		}
		/* Verify equality */
		ok = false;
		for(Input i : this.getInputs()){
			if(i.equals(input)){
				i.clear();
				i.setValues(input.getValues());
				ok = true;
				break;
			}
		}
		if(!ok){
			//System.out.println(getInputs().size() + ">=" + this.getConfiguration().getInputConfiguration().getMaximum());
			Preconditions.checkArgument(getInputs().size() < this.getConfiguration().getInputConfiguration().getMaximum(), 
					"Operation invalid. The maximum number of inputs was exceeded.");
			this.getInputs().add(input);
		}
	}
	
	public void addAllInput(List<Input> inputs) {
		this.inputs.addAll(inputs);
	}
	
	public void removeInput(Input input) {
		Preconditions.checkNotNull(input);
		this.getInputs().remove(input);
	}

	public void removeInput(int index) {
		Preconditions.checkArgument(index > 0 && index < this.getConfiguration().getInputConfiguration().getMaximum() - 1, "Invalid position.");
		this.getInputs().remove(index);
	}
	
	/*********************************************************************************************************/
	/*********************************************     Output      *******************************************/
	/*********************************************************************************************************/

	public void addOutput(Output output) {
		boolean ok = false;
		for(Type t : this.getConfiguration().getOutputConfiguration().getAvailableTypes()){
			if(output.getType().getClass().equals(t.getClass())){
				ok = true;
				break;
			}
		}
		if(!ok){
			throw new IllegalArgumentException("Mismatched types.");
		}
		/* Verify equality */
		ok = false;
		for(Output o : this.getOutputs()){
			if(o.equals(output)){
				o.setValues(output.getValues());
				ok = true;
				break;
			}
		}
		if(!ok){
			Preconditions.checkArgument(getOutputs().size() < this.getConfiguration().getOutputConfiguration().getMaximum(), 
					"Operation invalid. The maximum number of outputs was exceeded.");
			this.getOutputs().add(output);
		}
	}
	
	public void addAllOutput(List<Output> outputs) {
		this.outputs.addAll(outputs);
	}
	
	public void removeOutput(Output output) {
		Preconditions.checkNotNull(output);
		this.getOutputs().remove(output);
	}

	public void removeOutput(int index) {
		Preconditions.checkArgument(index > 0 && index < this.getConfiguration().getOutputConfiguration().getMaximum() - 1, "Invalid position.");
		this.getOutputs().remove(index);
	}
	
	/*********************************************************************************************************/
	/********************************************     Setting      *******************************************/
	/*********************************************************************************************************/

	public void addSetting(Setting setting) {
		if(getConfiguration().hasSettings() && this.getSettings() != null){
			for(Setting s : this.getSettings()){
				if(s.equals(setting)){
					s.getType().setValue(setting.getType().getValue());
					return;
				}
			}
			this.getSettings().add(setting);
		}
	}
	
	public void addAllSetting(List<Setting> settings) {
		this.settings.addAll(settings);
	}
	
	public List<Input> getInputs() {
		return this.inputs;
	}

	
	public List<Output> getOutputs() {
		return this.outputs;
	}

	public List<Setting> getSettings() {
		return this.settings;
	}
	
	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}
	
	public boolean isLoaded() {
		return this.loaded;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
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
		AbstractBundle other = (AbstractBundle) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}

	/**
	 * @return the inboundsLinks
	 */
	public List<Link> getInboundLinks() {
		return inboundLinks;
	}

	/**
	 * @param inboundsLinks the inboundsLinks to set
	 */
	public void setInboundLinks(List<Link> inboundsLinks) {
		this.inboundLinks = inboundsLinks;
	}

	/**
	 * @return the outboundsLinks
	 */
	public List<Link> getOutboundLinks() {
		return outboundLinks;
	}

	/**
	 * @param outboundsLinks the outboundsLinks to set
	 */
	public void setOutboundLinks(List<Link> outboundsLinks) {
		this.outboundLinks = outboundsLinks;
	}

	/**
	 * @return the graphics
	 */
	public List<AbstractGraphic> getGraphics() {
		return this.graphics;
	}
	
	/**
	 * @param graphics the graphics to set
	 */
	public void setGraphics(List<AbstractGraphic> graphics) {
		this.graphics = graphics;
	}
	
	@Override
	public void createGraphics() {}

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
	
	@Override
	public void createTextualReport() {}
}
