package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.Hotspot;
import com.vates.wifibus.backoffice.model.HotspotForm;
import com.vates.wifibus.backoffice.repository.HotspotRepository;

/**
 * Service: Hotspot service, used to handle hotspot info.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class HotspotServiceImpl implements HotspotService {

    private static final Logger logger = LoggerFactory.getLogger(HotspotServiceImpl.class);
    
	@Autowired
	private HotspotRepository hotspotRepository;

	@Override
	public Optional<Hotspot> getById(long id) {
		return Optional.ofNullable(hotspotRepository.findOne(id));
	}

	@Override
	public Optional<Hotspot> getByName(String hotspotName) {
    	return hotspotRepository.findOneByName(hotspotName);
	}
	
	@Override
	public Collection<Hotspot> getAll() {
		return hotspotRepository.findByEnabledTrueOrderByNameDesc();
	}

	@Override
	public Hotspot save(Hotspot hotspot) {
		return hotspotRepository.save(hotspot);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting Hotspot by id={}", id);
		hotspotRepository.deleteById(id);
	}

	@Override
	public Page<Hotspot> getHotspots(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return hotspotRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	public void addOrUpdateHotspot(HotspotForm hotspotForm) {
        if(hotspotForm.getId() != null){
        	hotspotRepository.setHotspotInfoByNameAndDescriptionAndRouteId(hotspotForm.getName(), 
        			hotspotForm.getDescripcion(), hotspotForm.getRouter().getId(), hotspotForm.getId());
        } else {
        	Hotspot hotspot = new Hotspot();
            BeanUtils.copyProperties(hotspotForm, hotspot);
        	hotspotRepository.save(hotspot);
        }	
	}

	@Override
	public Long countByName(String name) {
		return hotspotRepository.countByName(name);
	}
	
}