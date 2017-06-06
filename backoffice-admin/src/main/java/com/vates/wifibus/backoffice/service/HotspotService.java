package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.Hotspot;
import com.vates.wifibus.backoffice.model.HotspotForm;

/**
 * <TBD>
 * 
 * @author Luis Stubbia
 *
 */
public interface HotspotService extends EntityService<Hotspot> {
	
    /**
     * Get Hotspots into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<Hotspot>
     */
    Page<Hotspot> getHotspots(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a Hotspot.
     * @param Hotspot
     */
	void addOrUpdateHotspot(HotspotForm hotspot);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}