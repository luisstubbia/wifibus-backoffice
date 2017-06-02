package com.vates.wifibus.backoffice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.SecurityUser;
import com.vates.wifibus.backoffice.model.User;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.debug("Authenticating user with username={}", username);
		User user = userService.getByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with username=%s was not found", username)));
		
		return new SecurityUser(user);

	}

}
