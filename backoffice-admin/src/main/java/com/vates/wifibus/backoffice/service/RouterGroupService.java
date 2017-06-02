package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.RouterGroup;
import com.vates.wifibus.backoffice.model.RouterGroupForm;

/**
 * <TBD>
 * 
 * @author Luis Stubbia
 *
 */
public interface RouterGroupService extends EntityService<RouterGroup> {

	/**
	 * Get groups into a page structure
	 * @param pageNumber
	 * @param pageSize
	 * @param searchText
	 * @return
	 */
	Page<RouterGroup> getRouterGroups(Integer pageNumber, Integer pageSize, String searchText);

	/**
	 * Add or update group.
	 * @param routerGroupForm
	 */
	void addOrUpdateRouterGroup(RouterGroupForm routerGroupForm);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}
