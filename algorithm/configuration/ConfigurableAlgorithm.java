/**
 * 
 */
package br.ufpi.easii.athena.core.algorithm.configuration;

import br.ufpi.easii.athena.core.put.setting.Setting;

/**
 * @author Pedro Almir
 *
 */
public interface ConfigurableAlgorithm {
	
	/**
     * Add a setting in algorithm
     * @param setting
     * 			The setting representation
     */
    void addSetting(Setting setting);

    /**
     * Remove the specified setting.
     * @param setting
     * 			The setting representation
     */
    void removeSetting(Setting setting);
	
}
