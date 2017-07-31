package com.vates.wifibus.backoffice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.service.CampaignService;
import com.vates.wifibus.backoffice.api.service.ProfileService;
import com.vates.wifibus.backoffice.api.util.ServiceException;
import com.vates.wifibus.backoffice.model.Profile;

/**
 * API REST: Campañas
 * 
 * @author luis.stubbia
 *
 */
@RestController
public class CampaignRestController {

	@Autowired
	private CampaignService campaignService;

	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value = "/campaigns/{groupId}/{profileId}" , method = RequestMethod.GET)
	ResponseEntity<CampaignResponse> getAdvertisements(@PathVariable Long groupId, 
			@PathVariable Long profileId) throws Exception {
		Profile profile = profileService.getProfile(profileId);
		CampaignResponse campaign = campaignService.filterAdvertisements(groupId, profile);
		if (campaign.hasErrors()) {
			throw new ServiceException(campaign.getErrors());
		}
		return ResponseEntity.ok(campaign);
	}
	
	@RequestMapping(value = "/campaigns/{campaignId}" , method = RequestMethod.GET)
	ResponseEntity<CampaignResponse> getCampaing(@PathVariable Long campaignId) throws ServiceException {
		CampaignResponse campaign = campaignService.getCampaign(campaignId);
		if (campaign.hasErrors()) {
			throw new ServiceException(campaign.getErrors());
		}
		return ResponseEntity.ok(campaign);
	}
}