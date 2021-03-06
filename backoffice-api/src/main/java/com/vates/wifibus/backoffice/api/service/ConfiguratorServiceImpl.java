package com.vates.wifibus.backoffice.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vates.wifibus.backoffice.api.resource.ConfiguratorResponse;
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
	public ConfiguratorResponse getConfigurations(String hotspotName) {
		ConfiguratorResponse conf = new ConfiguratorResponse();
		if (!StringUtils.isEmpty(hotspotName)) {
			Optional<Hotspot> hotspot = hotspotRepository.findOneByName(hotspotName);
			if (hotspot.isPresent()) {
				Router router = routerRepository.findOne(hotspot.get().getRouter().getId());
				RouterGroup group = groupRepository.findOne(router.getGroup().getId());
				conf = new ConfiguratorResponse(group);
				conf.setConfig(app);
			} else {
				conf.addError(new BussinesError(ErrorCode.CONFIGURATION_NOT_FOUND));
			}
		} else {
			conf.addError(new BussinesError(ErrorCode.BAD_INPUT));
		}
		return conf;
	}
}