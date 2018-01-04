/**
 * 
 */
package br.ufpi.easii.athena.core.put.config;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import br.ufpi.easii.athena.core.type.base.Type;

/**
 * This class represents the minimum and maximum number of input/output module may have, 
 * as well as the associated types.
 * 
 * @author Pedro Almir
 *
 */
public class PutConfiguration {
	/**
	 * Minimum number of input/output
	 */
	private int minimum;
	/**
	 * Maximum number of input/output
	 */
	private int maximum;
	/**
	 * List of available types
	 */
	private List<Type> availableTypes;
	/** 
	 * Default names 
	 **/
	private LinkedHashSet<String> defaultNames;
	
	
	/**
	 * Default constructor 
	 */
	public PutConfiguration() {
		this.availableTypes = new ArrayList<Type>();
	}
	
	/**
	 * @return the minimum
	 */
	public int getMinimum() {
		return minimum;
	}
	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	/**
	 * @return the maximum
	 */
	public int getMaximum() {
		return maximum;
	}
	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	/**
	 * @return the types
	 */
	public List<Type> getAvailableTypes() {
		return availableTypes;
	}
	/**
	 * @param types the types to set
	 */
	public void setAvailableTypes(List<Type> types) {
		this.availableTypes = types;
	}
	/**
	 * Add available type
	 * @param type
	 */
	public void addAvailableType(Type type){
		this.availableTypes.add(type);
	}

	/**
	 * @return the defaultNames
	 */
	public List<String> getDefaultNames() {
		if(this.defaultNames != null){
			return new ArrayList<String>(this.defaultNames);
		}else{
			return null;
		}
	}
	
	/**
	 * @param names
	 */
	public void createDefaultNameList(LinkedHashSet<String> names){
		if(this.minimum == this.maximum && names.size() == this.minimum){
			this.defaultNames = new LinkedHashSet<String>();
			for(String name : names){
				this.defaultNames.add(name.toLowerCase().replaceAll(" ", "_"));
			}
		}
	}

	/**
	 * @param defaultNames the defaultNames to set
	 */
	public void setDefaultNames(LinkedHashSet<String> defaultNames) {
		this.defaultNames = defaultNames;
	}
}
