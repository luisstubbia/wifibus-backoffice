package com.vates.wifibus.backoffice.api.service;

import com.vates.wifibus.backoffice.api.resource.Configurator;

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
	 * @param hotspotId
	 * @return configurator resource
	 */
	Configurator getConfigurations(Long hotspotId);

}
