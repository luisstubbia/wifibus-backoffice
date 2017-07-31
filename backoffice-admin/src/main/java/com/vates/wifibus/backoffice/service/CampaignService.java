package com.vates.wifibus.backoffice.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.CampaignForm;

/**
 * Service: branding service
 * 
 * @author Luis Stubbia
 *
 */
public interface CampaignService extends EntityService<Campaign> {
	
    /**
     * Get Campaigns into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<Campaign>
     */
    Page<Campaign> getCampaigns(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a Campaign.
     * @param brand form
     */
	void addOrUpdateCampaign(CampaignForm campaign);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
	/**
	 * Get campaigns by default
	 * @param isDefault
	 * @return
	 */
	Collection<Campaign> getCampaignsByDefault(boolean isDefault);
}