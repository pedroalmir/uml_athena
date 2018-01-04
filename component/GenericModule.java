/**
 * 
 */
package br.ufpi.easii.athena.core.component;

import java.util.List;

import br.ufpi.easii.athena.core.algorithm.Algorithm;
import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.put.Input;
import br.ufpi.easii.athena.core.put.Output;
import br.ufpi.easii.athena.core.put.setting.Setting;
import br.ufpi.easii.athena.core.solution.Solution;

/**
 * This interface is designed to represent the modules of the tool.
 * 
 * @author Pedro Almir
 *
 */
public interface GenericModule extends AthenaBundle {
	/**
	 * Use Factory Pattern to create Algorithm object
	 * @return algorithm
	 */
	Algorithm getAlgorithm();
	/**
	 * @return <code>true</code> if is optimization algorithm
	 */
	boolean isOptimizationAlgorithm();
	/**
	 * Load and prepare this module for execution.
	 * 
	 * @param inputs
	 * @param outputs
	 * @param settings
	 */
	void load(List<Input> inputs, List<Output> outputs, List<Setting> settings);
	/**
	 * Load and prepare this module for execution.
	 */
	void load();
	/**
	 * Verify if this module is loaded.
	 * 
	 * @return <code>true</code> if the module is loaded.
	 */
	boolean isLoaded();
	/**
	 * Verify if this module is public.
	 * @return <code>true</code> if the module is public.
	 */
	boolean isPublic();
	/**
	 * Run the algorithm and return a list of solution to the problem.
	 * 
	 * @return solutions
	 */
	List<Solution> run();
}
