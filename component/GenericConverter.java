/**
 * 
 */
package br.ufpi.easii.athena.core.component;

import java.util.List;

import br.ufpi.easii.athena.core.component.base.AthenaBundle;
import br.ufpi.easii.athena.core.put.Output;

/**
 * This interface was designed to support the creation of converters.
 * 
 * @author Pedro Almir
 *
 */
public interface GenericConverter extends AthenaBundle {
	/**
	 * Convert inputs to specified outputs.
	 * 
	 * For example:
	 * CSV Converter: CSV -> List\<Object\>
	 * 
	 * This method only receives the input data, performs the conversion 
	 * and prepare them for the exit, returning a list of Output. 
	 * The method responsible for propagating this information to the 
	 * following module is in the class Link.
	 *  
	 * @return list of outputs
	 */
	List<Output> convert();
}
