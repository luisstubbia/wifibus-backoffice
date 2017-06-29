package com.vates.wifibus.backoffice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.ProfileRequest;
import com.vates.wifibus.backoffice.api.resource.ProfileResponse;
import com.vates.wifibus.backoffice.api.service.ProfileService;
import com.vates.wifibus.backoffice.api.util.ServiceException;

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

	@RequestMapping(value = "/profiles/{profileId}", method = RequestMethod.GET)
	ResponseEntity<ProfileResponse> getProfile(@PathVariable Long profileId) {
		ProfileResponse profile = profileService.getProfile(profileId);
		if (profile.hasErrors()) {
			new ServiceException(profile.getErrors());
		}
		return ResponseEntity.ok(profile);
	}
	
	@RequestMapping(value = "/profiles", method = RequestMethod.POST)
	ResponseEntity<ProfileResponse> getProfile(@RequestBody ProfileRequest profileReq) {
		ProfileResponse profile = profileService.addOrUpdateProfile(profileReq.toModel());
		if (profile.hasErrors()) {
			new ServiceException(profile.getErrors());
		}
		return ResponseEntity.ok(profile);
	}
}