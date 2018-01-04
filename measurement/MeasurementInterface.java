package br.ufpi.easii.athena.core.measurement.base;

import br.ufpi.easii.athena.core.algorithm.Algorithm;
import br.ufpi.easii.athena.core.type.base.Type;


/**
 * All measurements must implement this interface. The measurement must return
 * the value of what it is measuring given the algorithm that it is measuring.
 *
 * @param <E> The return {@code Type}.
 */
public interface MeasurementInterface<E extends Type> extends Cloneable {
	
	/**
	 * @return Measurement Name
	 */
	String getName();
	
    /**
     * {@inheritDoc}
     */
    MeasurementInterface<E> getClone();

    /**
     * Gets the value of the measurement. The representation of the measurement will be based
     * on the domain string defined.
     *
     * @param algorithm The algorithm to obtain the measurement from.
     * @return The <tt>Type</tt> representing the value of the measurement.
     */
    E getValue(Algorithm algorithm);
    
    /**
     * Get Stored value
     * */
    E getValue();
}
