package com.vates.wifibus.backoffice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vates.wifibus.backoffice.model.SecurityUser;

/**
 * 
 * @author Gaston Napoli
 *
 */
@ControllerAdvice
public class SecurityUserControllerAdvice {

	@ModelAttribute("securityUser")
	public SecurityUser getSecurityUser(Authentication authentication) {
		return (authentication == null) ? null : (SecurityUser) authentication.getPrincipal();
	}

}