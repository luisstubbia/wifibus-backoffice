package com.vates.wifibus.backoffice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.Configurator;
import com.vates.wifibus.backoffice.api.service.ConfiguratorService;
import com.vates.wifibus.backoffice.api.util.ServiceException;

/**
 * API REST: Configurations
 * 
 * @author luis.stubbia
 *
 */
@RestController
@RequestMapping("/configurations/{hotspotId}")
public class ConfiguratorRestController {

	@Autowired
	private ConfiguratorService configuratorService;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<Configurator> getCOnfigurations(@PathVariable Long hotspotId) {
		Configurator configs = configuratorService.getConfigurations(hotspotId);
		if(configs.hasErrors()){
			new ServiceException(configs.getErrors());
		}
		return ResponseEntity.ok(configs);
	}
}