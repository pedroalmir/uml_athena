/**
 * 
 */
package br.ufpi.easii.athena.core.algorithm.factory;

import br.ufpi.easii.athena.core.algorithm.Algorithm;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;


/**
 * @author Pedro Almir
 *
 */
public abstract class AbstractAlgorithmFactory implements AlgorithmFactory {
	
	/**
	 * @param algorithm
	 * @param input
	 */
	void addInput(Algorithm algorithm, Input input){
		
	}
	/**
	 * @param algorithm
	 * @param output
	 */
	void addOutput(Algorithm algorithm, Output output){
		
	}
	/**
	 * @param algorithm
	 * @param input
	 */
	void removeInput(Algorithm algorithm, Input input){
		
	}
	/**
	 * @param algorithm
	 * @param output
	 */
	void removeOutput(Algorithm algorithm, Output output){
		
	}
	/**
	 * @param algorithm
	 * @param setting
	 */
	void addSetting(Algorithm algorithm, Setting setting){
		
	}
	/**
	 * @param algorithm
	 * @param setting
	 */
	void removeSetting(Algorithm algorithm, Setting setting){
		
	}
}
