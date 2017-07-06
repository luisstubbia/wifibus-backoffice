package com.vates.wifibus.backoffice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.ConfiguratorResponse;
import com.vates.wifibus.backoffice.api.service.ConfiguratorService;
import com.vates.wifibus.backoffice.api.util.ServiceException;

/**
 * API REST: Configuraciones
 * 
 * @author luis.stubbia
 *
 */
@RestController
@RequestMapping("/configurations/{hotspotName}")
public class ConfiguratorRestController {

	@Autowired
	private ConfiguratorService configuratorService;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<ConfiguratorResponse> getConfigurations(@PathVariable String hotspotName) {
		ConfiguratorResponse configs = configuratorService.getConfigurations(hotspotName);
		if (configs.hasErrors()) {
			new ServiceException(configs.getErrors());
		}
		return ResponseEntity.ok(configs);
	}
}