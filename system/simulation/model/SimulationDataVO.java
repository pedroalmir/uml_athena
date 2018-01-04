package br.ufpi.easii.athena.core.system.simulation.model;

import java.util.LinkedList;
import java.util.List;

import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.setting.Setting;
import br.ufpi.easii.athena.web.model.vo.put.InputVO;
import br.ufpi.easii.athena.web.model.vo.put.SettingVO;

/**
 * This class represents a simulation data
 * @author Pedro Almir
 *
 */
public class SimulationDataVO{
	/**
	 * List of inputs used in this bundle
	 */
	private final List<InputVO> inputs;
	/**
	 * List of settings used in this bundle
	 */
	private final List<SettingVO> settings;
	/**
	 * @param bundle
	 * @param inputs
	 * @param outputs
	 */
	public SimulationDataVO(List<Input> inputs, List<Setting> settings) {
		this.inputs = new LinkedList<InputVO>();
		for(Input in : inputs){
			this.inputs.add(new InputVO(in));
		}
		this.settings = new LinkedList<SettingVO>();
		for(Setting setting : settings){
			this.settings.add(new SettingVO(setting));
		}
	}
	/**
	 * @param simulationData
	 */
	public SimulationDataVO(SimulationData simulationData) {
		this.inputs = new LinkedList<InputVO>();
		if(simulationData.getInputs() != null){
			for(Input in : simulationData.getInputs()){
				this.inputs.add(new InputVO(in));
			}
		}
		this.settings = new LinkedList<SettingVO>();
		if(simulationData.getSettings() != null){
			for(Setting setting : simulationData.getSettings()){
				this.settings.add(new SettingVO(setting));
			}
		}
	}
	/**
	 * @return the inputs
	 */
	public List<InputVO> getInputs() {
		return inputs;
	}
	/**
	 * @return the settings
	 */
	public List<SettingVO> getSettings() {
		return settings;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimulationDataVO [inputs=" + inputs + ", settings=" + settings + "]";
	}
}
