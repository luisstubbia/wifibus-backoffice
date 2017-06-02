package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vates.wifibus.backoffice.model.Hotspot;
import com.vates.wifibus.backoffice.model.HotspotForm;

/**
 * <TBD>
 * 
 * @author Luis Stubbia
 *
 */
@SessionAttributes(types = HotspotForm.class)
public interface HotspotService extends EntityService<Hotspot> {
	
    /**
     * Get Hotspots into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<Router>
     */
    Page<Hotspot> getHotspots(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a Hotspot.
     * @param router
     */
	void addOrUpdateHotspot(HotspotForm router);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}