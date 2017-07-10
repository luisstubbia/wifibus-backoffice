package com.vates.wifibus.backoffice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.resource.ProfileRequest;
import com.vates.wifibus.backoffice.api.service.CampaignService;
import com.vates.wifibus.backoffice.api.service.ProfileService;
import com.vates.wifibus.backoffice.api.util.ServiceException;
import com.vates.wifibus.backoffice.model.Profile;

/**
 * API REST: Perfil de Usuario
 * 
 * @author luis.stubbia
 *
 */
@RestController
public class ProfileRestController {
	
	@Autowired
	private ProfileService profileService;

	@Autowired
	private CampaignService campaignService;
		
	@RequestMapping(value = "/profiles/{campaignId}", method = RequestMethod.POST)
	ResponseEntity<CampaignResponse> setProfile(@PathVariable Long campaignId, 
			@RequestBody ProfileRequest profileReq) throws Exception {
		Profile profile = profileService.addOrUpdateProfile(profileReq.toModel());
		CampaignResponse campaign = campaignService.filterAdvertisements(campaignId, profile);
		if (campaign.hasErrors()) {
			throw new ServiceException(campaign.getErrors());
		}
		return ResponseEntity.ok(campaign);
	}
	
	@RequestMapping(value = "/profiles/{profileId}", method = RequestMethod.GET)
	ResponseEntity<Profile> getProfile(@PathVariable Long profileId) throws Exception {
		Profile profile = profileService.getProfile(profileId);
		return ResponseEntity.ok(profile);
	}
}