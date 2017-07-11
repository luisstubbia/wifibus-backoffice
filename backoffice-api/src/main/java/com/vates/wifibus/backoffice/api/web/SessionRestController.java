package com.vates.wifibus.backoffice.api.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.api.resource.SessionResponse;
import com.vates.wifibus.backoffice.api.service.ProfileService;
import com.vates.wifibus.backoffice.api.util.ServiceException;
import com.vates.wifibus.backoffice.model.Profile;

/**
 * API REST: Session.
 * 
 * @author luis.stubbia
 *
 */
@RestController
public class SessionRestController {

	@Autowired
	private ProfileService profileService;

	@Autowired
	RedisTemplate<String, Object> redis;

	@RequestMapping(value = "/sessions/{macAddress}/{profileId}", method = RequestMethod.POST)
	ResponseEntity<SessionResponse> createNewSession(@PathVariable String macAddress, @PathVariable Long profileId) throws ServiceException {
		Profile profile = profileService.getProfile(profileId);
		SessionResponse session = new SessionResponse(profile, macAddress);
		redis.opsForValue().set(macAddress, session);
		return ResponseEntity.ok(session);
	}

	@RequestMapping(value = "/sessions/{macAddress}", method = RequestMethod.GET)
	ResponseEntity<SessionResponse> getSessionRequest(@PathVariable String macAddress) throws ServiceException {
		SessionResponse session = getSession(macAddress);
		if(session == null) {
			throw new ServiceException(new BussinesError(ErrorCode.SESSION_NOT_FOUND));
		}
		return ResponseEntity.ok(session);
	}
	
	@RequestMapping(value = "/sessions/{macAddress}", method = RequestMethod.DELETE)
	ResponseEntity<String> deleteSession(@PathVariable String macAddress){
		if(redis.hasKey(macAddress)){
			redis.delete(macAddress);
		}
		return ResponseEntity.ok("Ok");
	}
	
	@RequestMapping(value = "/sessions", method = RequestMethod.GET)
	ResponseEntity<Set<String>> getSessions(){
		return ResponseEntity.ok(redis.keys("*"));
	}
	
	/**
	 * Get session object from Redis template.
	 * @param macAddress
	 * @return session value.
	 */
	private SessionResponse getSession(String macAddress) {
		SessionResponse session = null;
		if(redis.hasKey(macAddress)){
			session = (SessionResponse) redis.opsForValue().get(macAddress);
		}
		return session;
	}
}
