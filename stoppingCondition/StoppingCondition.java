package br.ufpi.easii.athena.core.stoppingCondition.base;

import com.google.common.base.Predicate;

import br.ufpi.easii.athena.core.algorithm.Algorithm;

/**
 * A class that implements this interface can be used to measure the progress of
 *  an algorithm. Primarily, subclasses of this interface are used to determine
 * the stopping criteria for an {@link Algorithm}. Stopping conditions are
 * applied to algorithms using StoppingCondition
 * <p>
 * Stopping conditions are also useful for implementing graphical progress bars and varying inertia
 * weights etc.
 * 
 * Obs.: interface Predicate<T>
 * Determines a true or false value for a given input. For example, a RegexPredicate might implement 
 * Predicate<String>, and return true for any string that matches its given regular expression.
 */
public interface StoppingCondition<T extends Algorithm> extends Predicate<T>, Cloneable {

    /**
     * Determines the percentage complete for the associated algorithm.
     * @return the percentage completed as a fraction {@literal (0 <= i <= 1.0)}.
     */
    public double getPercentageCompleted(T algorithm);

    /**
     * {@inheritDoc}
     */
    public StoppingCondition<T> getClone();
}
