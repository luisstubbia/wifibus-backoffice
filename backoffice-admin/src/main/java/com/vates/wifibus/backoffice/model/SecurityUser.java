package com.vates.wifibus.backoffice.model;

import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Datos de usuario para autenticacion y autorizacion con Spring Security.
 * 
 * @author Gaston Napoli
 *
 */
public class SecurityUser extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;

	/**
	 * @param user
	 */
	public SecurityUser(User user) {

		super(user.getUsername(), user.getPasswordHash(), user.isEnabled(), true, true, true,
				AuthorityUtils.createAuthorityList(user.getRole().toString()));

		this.user = user;

	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return user.getId();
	}

	public Role getRole() {
		return user.getRole();
	}

	public String getFullName() {
		return user.getFullName();
	}
	
	@Override
	public String toString() {
		return "SecurityUser{" + "user=" + user + "} " + super.toString();
	}

}
