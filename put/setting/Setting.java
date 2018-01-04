/**
 *           %%%                       .#%#                                                              
 *          % %%%          .#%#        .#%#                                                              
 *         %%  %%%       .##%%%###     .#%#%%%%%%#%        %%#%%%%%    .##### %%%%%#.        %#%%%%##     
 *        %%    %%%        .#%%        .#%#      #%#.     %#%%   %%%.     .%%%%%%%%%%%.     %%     %%#    
 *       %%      %%%       .#%%        .#%#      #%#.   %%%%     %%%.    .%%.      #%#.           .%%.   
 *      %%        %%%       .#%%       .#%#      #%#.   %%%%%%%%%%%%.    .%%.      #%#.      .#####%%.   
 *     %% ######## %%%      .#%%       .#%#      #%#.    %%              .%%.      #%#.    #%##   .%%.   
 *    %%            %%%      #%%       .#%#      #%#.    %%%             .%%.      #%#.   #%%     .%%.   
 *   %%              %%%     .#%       .#%#      #%#.     %%%.           .%%.      #%%.    %%%   %%%%.   
 *  ####          ######      ####  .######   ####%#.     ##%%%%##   .#######  ########     %%%## %%%%.
 *  
 *  Artificial Intelligence as a Service
 *  Lab. EaSII
 *  
 *  @author Pedro Almir
 *  @author Ronyerison Braga
 *  @author Matheus Campanhã
 */
package br.ufpi.easii.athena.core.put.setting;

import java.io.Serializable;
import java.util.List;

import br.ufpi.easii.athena.core.put.base.Put;
import br.ufpi.easii.athena.core.put.validation.Validation;
import br.ufpi.easii.athena.core.type.base.Type;

/**
 * This class represents a setting for any algorithm or module.<br>
 * In this case, the field type store the real value of setting.<br>
 * 
 * @author Pedro Almir
 */
public class Setting extends Put {
	
	/** Serial version UID */
	private static final long serialVersionUID = -1896325039622417404L;
	private boolean required;
	private String value;
	private String description;
	private Serializable defaultValue;
	
	/**
	 * Default constructor
	 */
	public Setting() {
		this.required = true;
	}

	/**
	 * Setting constructor
	 * 
	 * @param name
	 * @param identifier
	 * @param type
	 * @param representation
	 * @param multipleValues
	 * @param validations
	 */
	public Setting(String name, String identifier, Type type, String representation, boolean multipleValues, List<Validation> validations) {
		super(identifier, name, type, representation, multipleValues, validations);
		this.required = true;
	}
	
	/**
	 * @param name
	 * @param identifier
	 * @param type
	 * @param representation
	 * @param description
	 * @param multipleValues
	 * @param validations
	 * @param required
	 */
	public Setting(String name, String identifier, Type type, String representation, String description, boolean multipleValues, List<Validation> validations, boolean required) {
		super(identifier, name, type, representation, multipleValues, validations);
		this.required = required;
		this.description = description;
	}
	
	/**
	 * @param identifier
	 * @param name
	 * @param type
	 * @param representation
	 * @param description
	 * @param defaultValue
	 * @param multipleValues
	 * @param validations
	 * @param required
	 */
	public Setting(String identifier, String name, Type type, String representation, String description, Serializable defaultValue, boolean multipleValues, List<Validation> validations, boolean required) {
		super(identifier, name, type, representation, multipleValues, validations);
		this.required = required;
		this.description = description;
		this.defaultValue = defaultValue;
	}
	
	/**
	 * @param name
	 * @param identifier
	 * @param type
	 * @param representation
	 * @param multipleValues
	 * @param validations
	 * @param required
	 */
	public Setting(String name, String identifier, Type type, String representation, boolean multipleValues, List<Validation> validations, boolean required) {
		super(identifier, name, type, representation, multipleValues, validations);
		this.required = required;
	}
	
	/**
	 * @param setting
	 */
	public Setting(Setting setting) {
		super(setting.getIdentifier(), setting.getName(), setting.getType().getClone(), setting.getRepresentation(), setting.isMultipleValues(), setting.getValidations());
		this.required = setting.isRequired();
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Setting clone() throws CloneNotSupportedException {
		return new Setting(this);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the defaultValue
	 */
	public Serializable getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(Serializable defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}
