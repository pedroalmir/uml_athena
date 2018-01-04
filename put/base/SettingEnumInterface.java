/**
 * 
 */
package br.ufpi.easii.athena.core.put.base;

import br.ufpi.easii.athena.core.type.base.Type;

/**
 * @author PedroAlmir
 */
public interface SettingEnumInterface {
	/** Get description of setting */
	String getDescription();
	/** Get type of setting */
	Type getType();

	/**
	 * @return the identifier
	 */
	String getIdentifier();

	/**
	 * @return the name
	 */
	public String getName();
}
