package br.ufpi.easii.athena.core.stoppingCondition.base;

import br.ufpi.easii.athena.core.algorithm.Algorithm;

/**
 *
 */
public interface Stoppable {

    /**
     * Add a stopping condition
     * @param condition The stopping condition to add.
     */
    void addStoppingCondition(StoppingCondition<Algorithm> condition);

    /**
     * Remove the specified stopping condition.
     * @param condition The stopping condition to remove.
     */
    void removeStoppingCondition(StoppingCondition<Algorithm> condition);

}
