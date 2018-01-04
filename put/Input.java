/**
 * 
 */
package br.ufpi.easii.athena.core.put;

import java.util.List;

import br.ufpi.easii.athena.core.put.base.Put;
import br.ufpi.easii.athena.core.put.validation.Validation;
import br.ufpi.easii.athena.core.type.base.Type;
import br.ufpi.easii.athena.core.type.container.list.TypeList;


/**
 * This class represents an input in Athena Service.
 * @author Pedro Almir
 */
public class Input extends Put {
	/** Serial Version UID */
	private static final long serialVersionUID = -3685429284838660600L;
	/** This field represents a list of values that this input contains. */
	private TypeList input;
	/** Linked */
	private boolean linked;
	
	/**
	 * Default constructor
	 */
	public Input() {
		this.input = new TypeList();
	}
	
	/**
	 * @param input
	 */
	public Input(Input copy) {
		super(copy.getIdentifier(), copy.getName(), copy.getType(), copy.getRepresentation(), copy.isMultipleValues(), copy.getValidations());
		this.input = copy.input.getClone();
		this.linked = copy.isLinked();
	}
	
	/**
	 * @param values
	 */
	public Input(String name, String identifier, Type type, String representation, boolean multipleValues, List<Validation> validations) {
		super(identifier, name, type, representation, multipleValues, validations);
		this.input = new TypeList();
		this.linked = false;
	}
	
	/**
	 * @param type
	 */
	public void addValue(Type type){
		this.input.add(type);
	}

	/**
	 * @return the values
	 */
	public List<Type> getValues() {
		return input.getComponents();
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Type> values) {
		this.input.addAll(values);
	}
	
	/**
	 * 
	 */
	public void clear(){
		this.input.clear();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Input clone() throws CloneNotSupportedException {
		return new Input(this);
	}

	/**
	 * @return the linked
	 */
	public boolean isLinked() {
		return linked;
	}

	/**
	 * @param linked the linked to set
	 */
	public void setLinked(boolean linked) {
		this.linked = linked;
	}

}
