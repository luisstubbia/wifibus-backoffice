package com.vates.wifibus.backoffice.api.service;

import com.vates.wifibus.backoffice.api.resource.ConfiguratorResponse;

/**
 * Servicio para manejar las configuraciones necesarias, basadas en un hotspot.
 * 
 * @author luis.stubbia
 *
 */
public interface ConfiguratorService {

	/**
	 * Busca las configuraciones aplicables a un hotspot particular.
	 * 
	 * @param hotspotName
	 * @return configurator resource
	 */
	ConfiguratorResponse getConfigurations(String hotspotName);

}
