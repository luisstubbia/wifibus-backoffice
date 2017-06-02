package com.vates.wifibus.backoffice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.Role;
import com.vates.wifibus.backoffice.model.SecurityUser;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Service
public class SecurityUserServiceImpl implements SecurityUserService {

	private static final Logger logger = LoggerFactory.getLogger(SecurityUserServiceImpl.class);

	@Override
	public boolean canAccessUser(SecurityUser securityUser, Long userId) {
		
		logger.debug("Verificando si usuario {} tiene acceso a usuario {}", securityUser, userId);
		
		return securityUser != null && (securityUser.getRole() == Role.ADMIN || securityUser.getId().equals(userId));

	}

}
