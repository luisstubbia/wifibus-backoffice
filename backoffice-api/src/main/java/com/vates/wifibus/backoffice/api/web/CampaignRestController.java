package com.vates.wifibus.backoffice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.resource.FilterRequest;
import com.vates.wifibus.backoffice.api.service.CampaignService;
import com.vates.wifibus.backoffice.api.util.ServiceException;

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

	@RequestMapping(value = "/campaigns/{campaignId}/{profileId}" , method = RequestMethod.POST)
	ResponseEntity<CampaignResponse> getAdvertisements(@PathVariable Long campaignId, @PathVariable Long profileId, 
			@RequestBody FilterRequest filter) {
		CampaignResponse campaign = campaignService.filterAdvertisements(campaignId, profileId, filter);
		if (campaign.hasErrors()) {
			new ServiceException(campaign.getErrors());
		}
		return ResponseEntity.ok(campaign);
	}
	
	@RequestMapping(value = "/campaigns/{campaignId}" , method = RequestMethod.GET)
	ResponseEntity<CampaignResponse> getCampaing(@PathVariable Long campaignId) {
		CampaignResponse campaign = campaignService.getCampaign(campaignId);
		if (campaign.hasErrors()) {
			new ServiceException(campaign.getErrors());
		}
		return ResponseEntity.ok(campaign);
	}
}