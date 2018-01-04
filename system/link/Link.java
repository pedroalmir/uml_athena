/**
 * 
 */
package br.ufpi.easii.athena.core.system.link;

import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.type.base.Type;

/**
 * This class represents the connection (link) between two modules.
 * It's responsible for propagating the data given output for a given input.
 * 
 * @author Pedro Almir
 *
 */
public class Link{
	/** This field must be unique! */
	private String identifier;
	/** Description label */
	private String description;
	
	/** Source module */
	private AthenaBundle srcModule;
	/** Source output */
	private Output srcOutput;
	
	/** Destination module */
	private AthenaBundle dstModule;
	/** Destination input */
	private Input dstInput;
	
	/**
	 * @param description
	 * @param srcModule
	 * @param dstModule
	 * @param srcOutput
	 * @param dstInput
	 */
	public Link(String identifier, String description, AthenaBundle srcModule, AthenaBundle dstModule, Output srcOutput, Input dstInput) {
		this.identifier = identifier;
		this.description = description;
		this.srcModule = srcModule;
		this.dstModule = dstModule;
		this.srcOutput = srcOutput;
		this.dstInput = dstInput;
	}
	
	/** Propagate the results */
	public void propagate(){
		for(Type value : this.srcOutput.getValues()){
			this.dstInput.addValue(value);
		}
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
	 * @return the srcModule
	 */
	public AthenaBundle getSrcModule() {
		return srcModule;
	}
	/**
	 * @param srcModule the srcModule to set
	 */
	public void setSrcModule(AthenaBundle srcModule) {
		this.srcModule = srcModule;
	}
	/**
	 * @return the dstModule
	 */
	public AthenaBundle getDstModule() {
		return dstModule;
	}
	/**
	 * @param dstModule the dstModule to set
	 */
	public void setDstModule(AthenaBundle dstModule) {
		this.dstModule = dstModule;
	}
	/**
	 * @return the srcOutput
	 */
	public Output getSrcOutput() {
		return srcOutput;
	}
	/**
	 * @param srcOutput the srcOutput to set
	 */
	public void setSrcOutput(Output srcOutput) {
		this.srcOutput = srcOutput;
	}
	/**
	 * @return the dstInput
	 */
	public Input getDstInput() {
		return dstInput;
	}
	/**
	 * @param dstInput the dstInput to set
	 */
	public void setDstInput(Input dstInput) {
		this.dstInput = dstInput;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Link [description=" + description + ", srcModule=" + srcModule + ", dstModule=" + dstModule + ", srcOutput=" + srcOutput + ", dstInput=" + dstInput + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dstInput == null) ? 0 : dstInput.hashCode());
		result = prime * result
				+ ((dstModule == null) ? 0 : dstModule.hashCode());
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result
				+ ((srcModule == null) ? 0 : srcModule.hashCode());
		result = prime * result
				+ ((srcOutput == null) ? 0 : srcOutput.hashCode());
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
		Link other = (Link) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (srcModule == null) {
			if (other.srcModule != null)
				return false;
		} else if (!srcModule.equals(other.srcModule))
			return false;
		if (srcOutput == null) {
			if (other.srcOutput != null)
				return false;
		} else if (!srcOutput.equals(other.srcOutput))
			return false;
		if (dstModule == null) {
			if (other.dstModule != null)
				return false;
		} else if (!dstModule.equals(other.dstModule))
			return false;
		if (dstInput == null) {
			if (other.dstInput != null)
				return false;
		} else if (!dstInput.equals(other.dstInput))
			return false;
		return true;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
