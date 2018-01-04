package br.ufpi.easii.athena.core.system.simulation.model;

import java.util.List;

import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;

/**
 * This class represents a simulation data
 * @author Pedro Almir
 *
 */
public class SimulationData{
	/**
	 * AthenaBundle without data
	 */
	private final AthenaBundle bundle;
	/**
	 * List of inputs used in this bundle
	 */
	private final List<Input> inputs;
	/**
	 * List of outputs used in this bundle
	 */
	private List<Output> outputs;
	/**
	 * List of settings used in this bundle
	 */
	private final List<Setting> settings;
	/**
	 * @param bundle
	 * @param inputs
	 * @param outputs
	 */
	public SimulationData(AthenaBundle bundle, List<Input> inputs, List<Output> outputs, List<Setting> settings) {
		this.bundle = bundle;
		this.inputs = inputs;
		this.outputs = outputs;
		this.settings = settings;
	}
	/**
	 * @return the bundle
	 */
	public AthenaBundle getBundle() {
		return bundle;
	}
	/**
	 * @return the inputs
	 */
	public List<Input> getInputs() {
		return inputs;
	}
	/**
	 * @return the outputs
	 */
	public List<Output> getOutputs() {
		return outputs;
	}
	/**
	 * @return the settings
	 */
	public List<Setting> getSettings() {
		return settings;
	}
	/**
	 * @param outputs the outputs to set
	 */
	public void setOutputs(List<Output> outputs) {
		this.outputs = outputs;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		result = prime * result
				+ ((settings == null) ? 0 : settings.hashCode());
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
		SimulationData other = (SimulationData) obj;
		if (bundle == null) {
			if (other.bundle != null)
				return false;
		} else if (!bundle.equals(other.bundle))
			return false;
		if (inputs == null) {
			if (other.inputs != null)
				return false;
		} else if (!inputs.equals(other.inputs))
			return false;
		if (settings == null) {
			if (other.settings != null)
				return false;
		} else if (!settings.equals(other.settings))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimulationData [bundle=" + bundle.getName() + ", inputs=" + inputs + ", outputs=" + outputs + ", settings=" + settings + "]";
	}
}
