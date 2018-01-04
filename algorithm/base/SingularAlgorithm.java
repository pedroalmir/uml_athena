package br.ufpi.easii.athena.core.algorithm.base;

import br.ufpi.easii.athena.core.algorithm.Algorithm;
import br.ufpi.easii.athena.core.algorithm.configuration.ConfigurableAlgorithm;
import br.ufpi.easii.athena.core.stoppingCondition.base.Stoppable;

/**
 * This class is the abstract base for all algorithms that do not
 * fall within the normal Computational Intelligence type of algorithm.
 * <p>
 * Examples of such algorithms will include <tt>KMeans</tt>, <tt>Gradient Decent</tt> etc.
 *
 */
public interface SingularAlgorithm extends Algorithm, Stoppable, ConfigurableAlgorithm {

}
