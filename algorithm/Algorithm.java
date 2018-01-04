/**
 * 
 */
package br.ufpi.easii.athena.core.algorithm;

import br.ufpi.easii.athena.core.problem.base.Problem;
import br.ufpi.easii.athena.core.solution.Solution;



/**
 * Base interface defining all {@code Algorithm} classes.
 * 
 * @author Pedro Almir
 */
public interface Algorithm extends Runnable, Cloneable{
	/**
     * Perform the actions of the current {@linkplain Algorithm} for a single iteration.
     */
	void performIteration();
	/**
     * Perform the needed initialization required before the execution of the algorithm
     * starts.
     */
	void performInitialisation();
	
	/**
     * Obtain the best current solution.
     * @return The {@code OptimisationSolution} representing the best solution.
     */
    Solution getBestSolution();
    
	/**
     * Returns the number of iterations that have been performed by the algorithm.
     * 
     * @return The number of iterations.
     */
    int getIterations();
    /**
     * Set the optimization {@link Problem} to be solved.
     * <p>
     * By default, the problem is {@code null}<code>null</code>. It is necessary
     * to set the optimization problem before calling {@link #performInitialisation()}.
     *
     * @param problem an implementation of the {@link Problem} interface.
     */
    void setOptimizationProblem(Problem problem);

    /**
     * Get the specified {@linkplain Problem}.
     * @return The specified {@linkplain Problem}.
     */
    Problem getOptimizationProblem();
    /**
     * Verify if is finished
     * @return <code>true</code> if is finished
     */
    boolean isFinished();
}
