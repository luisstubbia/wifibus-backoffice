package com.vates.wifibus.backoffice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.Campaing;
import com.vates.wifibus.backoffice.api.resource.Filter;
import com.vates.wifibus.backoffice.api.service.CampaignService;
import com.vates.wifibus.backoffice.api.util.ServiceException;

/**
 * API REST: Profiles
 * 
 * @author luis.stubbia
 *
 */
@RestController
@RequestMapping("/campaigns/{campaignId}")
public class CampaignRestController {

	@Autowired
	private CampaignService campaignService;

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Campaing> getAdvertisements(@PathVariable Long campaignId, @RequestBody Filter filter) {
		Campaing campaign = campaignService.getAdvertisements(campaignId, filter);
		if (campaign.hasErrors()) {
			new ServiceException(campaign.getErrors());
		}
		return ResponseEntity.ok(campaign);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<Campaing> getCampaing(@PathVariable Long campaignId) {
		Campaing campaign = campaignService.getCampaign(campaignId);
		if (campaign.hasErrors()) {
			new ServiceException(campaign.getErrors());
		}
		return ResponseEntity.ok(campaign);
	}
}