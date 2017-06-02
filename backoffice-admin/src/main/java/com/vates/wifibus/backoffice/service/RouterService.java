package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.Router;
import com.vates.wifibus.backoffice.model.RouterForm;

/**
 * <TBD>
 * 
 * @author Luis Stubbia
 *
 */
public interface RouterService extends EntityService<Router> {

    /**
     * Get routers into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<Router>
     */
    Page<Router> getRouters(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a router.
     * @param router
     */
	void addOrUpdateRouter(RouterForm router);
	
	/**
	 * Returns number of duplicated macAddress already persisted 
	 * @param macAddresss
	 * @return long
	 */
	Long countByMacAddress(String macAddresss);
	
	/**
	 * Returns number of duplicated ipAddress already persisted.
	 * @param ipAddress
	 * @return long
	 */
	Long countByIpAddress(String ipAddress);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}
