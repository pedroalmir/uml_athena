
package br.ufpi.easii.athena.core.type.base;

/**
 * Interface to define bounds. These bounds are generally / currently defined
 * to be a single upper and lower bound. There may be situations whereby this
 * functionality may need to be extended to an arbitrary amount of bounds. This
 * is still very much in debate.
 *
 * @author Pedro Almir
 */
public interface BoundedType {

    /**
     * @return bounds
     */
    Bounds getBounds();

}
