package com.vates.wifibus.backoffice.service;

import com.vates.wifibus.backoffice.model.SecurityUser;

/**
 * 
 * @author Gaston Napoli
 *
 */
public interface SecurityUserService {

	boolean canAccessUser(SecurityUser securityUser, Long userId);
	
}
