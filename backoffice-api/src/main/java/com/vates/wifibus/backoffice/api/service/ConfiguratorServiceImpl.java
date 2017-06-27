package com.vates.wifibus.backoffice.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.api.resource.Configurator;
import com.vates.wifibus.backoffice.api.config.ApplicationConfiguration;
import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.model.Hotspot;
import com.vates.wifibus.backoffice.model.Router;
import com.vates.wifibus.backoffice.model.RouterGroup;
import com.vates.wifibus.backoffice.repository.HotspotRepository;
import com.vates.wifibus.backoffice.repository.RouterGroupRepository;
import com.vates.wifibus.backoffice.repository.RouterRepository;

/**
 * Servicio para manejar las configuraciones necesarias, basadas en un hotspot.
 * 
 * @author luis.stubbia
 *
 */
@Service
public class ConfiguratorServiceImpl implements ConfiguratorService {

	@Autowired
	private RouterRepository routerRepository;

	@Autowired
	private RouterGroupRepository groupRepository;

	@Autowired
	private ApplicationConfiguration app;
	
	@Autowired
	private HotspotRepository hotspotRepository;

	@Override
	public Configurator getConfigurations(Long hotspotId) {
		Configurator conf = new Configurator();
		if (hotspotId != null) {
			Hotspot hotspot = hotspotRepository.findOne(hotspotId);
			if (hotspot != null) {
				Router router = routerRepository.findOne(hotspot.getRouter().getId());
				RouterGroup group = groupRepository.findOne(router.getGroup().getId());
				conf = new Configurator(group);
				conf.setConfig(app);
				conf.populateLinks(group);
			} else {
				conf.addError(new BussinesError(ErrorCode.CONFIGURATION_NOT_FOUND));
			}
		} else {
			conf.addError(new BussinesError(ErrorCode.BAD_INPUT));
		}
		return conf;
	}
}