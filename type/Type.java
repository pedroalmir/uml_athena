/**
 * 
 */
package br.ufpi.easii.athena.core.type.base;

import br.ufpi.easii.athena.core.type.vo.base.TypeVO;


/**
 * This interface was designed to represent all
 * types accepted in this tool.
 * 
 * @author Pedro Almir
 *
 */
public interface Type extends Cloneable{
	 /**
     * {@inheritDoc}
     */
    Type getClone();
    /**
     * 
     */
    Type getClone(String object);
    /**
     * 
     */
    void clear();
    /**
     * Compare the specified object with this type for equality. Returns
     * {@code true} if and only if the specified object is also an instance
     * of the same type.
     * @param obj The object to compare.
     * @return {@code true} if equality exists, {@code false} otherwise.
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object obj);
    /**
     * Returns the hash code value for this list.  The hash code of a list
     * is defined to be the result of the following calculation:
     * <pre>
     *  int hashCode = 7;
     *  Iterator&lt;E&gt; i = list.iterator();
     *  while (i.hasNext()) {
     *      E obj = i.next();
     *      hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
     *  }
     * </pre>
     * This ensures that <tt>type1.equals(type2)</tt> implies that
     * <tt>type1.hashCode()==type2.hashCode()</tt> for any two types,
     * <tt>type1</tt> and <tt>type2</tt>, as required by the general
     * contract of {@link Object#hashCode}.
     *
     * @return the hash code value for this list
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    @Override
    int hashCode();
    /**
     * @return object value
     */
    Object getValue();
    /**
     * @param object
     */
    void setValue(Object object);
    /**
     * @param object
     */
    void setValue(String object);
    /**
     * @return object representation for this type
     */
    Object getRepresentation();
    /**
     * @return return the type visual object representation
     * for this object.
     */
    TypeVO getTypeVO();
}
