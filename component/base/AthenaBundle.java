/**
 * 
 */
package br.ufpi.easii.athena.core.component.base;

import java.util.List;

import br.ufpi.easii.athena.common.model.enums.AthenaBundleGroup;
import br.ufpi.easii.athena.core.component.configuration.Configuration;
import br.ufpi.easii.athena.core.measurement.base.Measurement;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;
import br.ufpi.easii.athena.core.system.link.Link;
import br.ufpi.easii.athena.core.type.container.graphic.base.AbstractGraphic;

/**
 * This interface was designed to represent any component to be integrated 
 * into the tool. Thus we can work in a general way, for example, in the case 
 * of links. A link may be a link from one module to another module or the 
 * connection between a module and a converter. 
 * 
 * Initially this interface has no methods defined.
 * 
 * @author Pedro Almir
 * @since 25/06/2013
 */
public interface AthenaBundle{
	
	/**
	 * This method should return the name of the module.
	 * @return the name of the module
	 */
	String getName();
	/**
	 * This method should return the short name of the module.
	 * @return the short name of the module
	 */
	String getShortName();
	/**
	 * This method should return the Athena Bundle Group.
	 * @return the group
	 */
	AthenaBundleGroup getGroup();
	/**
	 * @return the identifier
	 */
	String getIdentifier();
	/**
	 * @param identifier the identifier to set
	 */
	void setIdentifier(String identifier);
	/**
	 * This method should return the description of how
	 * the module works.
	 * This will help the user to understand the operation
	 * of the algorithm encapsulated by this module.
	 *  
	 * @return the description
	 */
	String getDescription();
	/**
	 * This method should return the file description path.
	 * This description must explain how the module works.
	 * This will help the user to understand the operation of
	 * the algorithm encapsulated by the module.*/
	String getFileDescriptionPath();
	/**
	 * This method should return a link to an image
	 * that represents the module.
	 * 
	 * @return the image path
	 */
	String getImagePath();
	/**
	 * This method should add an input to this module.
	 * For example:
	 * 
	 * Fuzzy Module can contain numerous entries. Using this
	 * method the user can add input to this module.
	 * 
	 * @param input object
	 */
	void addInput(Input input);
	/**
	 * Add all inputs
	 * @param inputs
	 */
	void addAllInput(List<Input> inputs);
	/**
	 * This method should return the list of inputs
	 * of this module.
	 * 
	 * @return list of inputs
	 */
	List<Input> getInputs();
	/**
	 * This method should add an output to this module.
	 * 
	 * @param output object
	 */
	void addOutput(Output output);
	/**
	 * Add all outputs
	 * @param outputs
	 */
	void addAllOutput(List<Output> outputs);
	/**
	 * This method should return the list of outputs
	 * of this module.
	 * 
	 * @return list of outputs
	 */
	List<Output> getOutputs();
	/**
	 * This method should add an setting to this module.
	 * 
	 * For example:
	 * Fuzzy Module need FCL config file.
	 * 
	 * @param setting object
	 */
	void addSetting(Setting setting);
	/**
	 * Add all settings
	 * @param settings
	 */
	void addAllSetting(List<Setting> settings);
	/**
	 * This method should return the list of settings
	 * of this module.
	 * 
	 * @return list of settings
	 */
	List<Setting> getSettings();
	/**
	 * @param settings
	 */
	void setSettings(List<Setting> settings);
	/**
	 * All module settings are stored in the Configuration object.
	 * So, this method should return the module configuration.
	 * 
	 * @return configuration
	 */
	Configuration getConfiguration();
	/**
	 * @param identifier
	 * @return setting
	 */
	Setting findSetting(String identifier);
	/**
	 * This method should remove an input
	 * @param input
	 * 			input to remove
	 */
	void removeInput(Input input);
	/**
	 * This method should remove an input.
	 * 
	 * @param index
	 * 			index of input to remove.
	 */
	void removeInput(int index);
	/**
	 * This method should remove an output.
	 * 
	 * @param output
	 * 			output to remove
	 */
	void removeOutput(Output output);
	/**
	 * This method should remove an output.
	 * 
	 * @param index
	 * 			index of output to remove
	 */
	void removeOutput(int index);
	/**
	 * @param allLinks
	 */
	void populateInAndOutboundLinks(List<Link> allLinks);
	
	/** @return */
	List<Link> getOutboundLinks();
	/** @param outboundsLinks */
	void setOutboundLinks(List<Link> outboundsLinks);
	
	/** @return the inboundsLinks */
	List<Link> getInboundLinks();
	/** @param inboundsLinks the inboundsLinks to set */
	void setInboundLinks(List<Link> inboundsLinks);
	
	/**
	 * @return list of measurements
	 */
	List<Measurement> getMeasurements();
	/**
	 * @param measurements
	 */
	void setMeasurements(List<Measurement> measurements);
	/**
	 * @param measurement
	 */
	void addMeasurement(Measurement measurement);
	/**
	 * Get graphics of the bundle execution
	 * @return this.graphics
	 * */
	List<AbstractGraphic> getGraphics();
	/**
	 * Create graphics
	 * */
	void createGraphics();
	/**
	 * @return textual report of the bundle execution
	 */
	String getTextualReport();
	/**
	 * Create the textual report of the bundle execution
	 */
	void createTextualReport();
}
