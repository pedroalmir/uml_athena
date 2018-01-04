/**
 * 
 */
package br.ufpi.easii.athena.core.solution;

/**
 * This interface represents a solution to an algorithm, 
 * when executed to solve any problem.
 * 
 * @author Pedro Almir 
 * TODO: Improve this implementation.
 */
public interface MultiobjectiveSolution {
	/**
	 * @return
	 */
	public Double getFitnessMaximization();
	/**
	 * @return
	 */
	public Double getFitnessMinimization();
}
