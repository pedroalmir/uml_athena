/**
 * 
 */
package br.ufpi.easii.athena.core.component.configuration;

import java.util.List;

import br.ufpi.easii.athena.core.put.config.PutConfiguration;
import br.ufpi.easii.athena.core.put.setting.Setting;

/**
 * @author PedroAlmir
 */
public class NewAbstractionConfiguration implements Configuration {
	
	private final PutConfiguration inputConfiguration;
	private final PutConfiguration outputConfiguration;
	private final List<Setting> settings;
	
	/**
	 * @param inputConfiguration
	 * @param outputConfiguration
	 * @param hasSettings
	 * @param settings
	 */
	public NewAbstractionConfiguration(PutConfiguration inputConfiguration, PutConfiguration outputConfiguration, List<Setting> settings) {
		this.inputConfiguration = inputConfiguration;
		this.outputConfiguration = outputConfiguration;
		this.settings = settings;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.configuration.Configuration#getInputConfiguration()
	 */
	@Override
	public PutConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.configuration.Configuration#getOutputConfiguration()
	 */
	@Override
	public PutConfiguration getOutputConfiguration() {
		return this.outputConfiguration;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.configuration.Configuration#getSettings()
	 */
	@Override
	public List<Setting> getSettings() {
		return this.settings;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.component.configuration.Configuration#hasSettings()
	 */
	@Override
	public boolean hasSettings() {
		return this.settings.size() > 0;
	}

}
