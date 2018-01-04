/**
 * 
 */
package br.ufpi.easii.athena.core.problem.base;

import br.ufpi.easii.athena.core.problem.base.objective.Objective;
import br.ufpi.easii.athena.core.solution.Solution;


/**
 * Optimization problems are characterized by a domain that specifies the search
 * space and a fitness given a potential solution. This interface ensures that
 * an Algorithm has all the information it needs to find a solution to a given 
 * optimization problem.
 * 
 * In addition, it is the responsibility of an optimization problem to keep
 * track of the number of times the fitness has been evaluated.
 * <p>
 * All optimization problems must implement this interface.
 * 
 * @author Pedro Almir
 */
public interface Problem extends Cloneable{
    /**
     * Returns the fitness of a potential solution to this problem. 
     * The solution object is described by the domain of this problem.
     *
     * @param solution  the potential solution found by the optimization algorithm.
     * @return          the fitness of the solution.
     */
    Double getFitness(Solution solution);
    /**
     * Check if this solution is suitable to the problem 
     * */
    boolean checkSolution(Solution solution);
    /**
     * @return problem objective
     */
    Objective getObjective();
}
