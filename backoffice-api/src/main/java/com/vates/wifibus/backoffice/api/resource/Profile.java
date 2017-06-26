package com.vates.wifibus.backoffice.api.resource;

import java.util.Map;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Recurso del perfil de usuario.
 * 
 * @author luis.stubbia
 *
 */
public class Profile extends BaseResource<Map<String, String>> {

	public static final String SESSION_LINK = "sessions";
	
	private String macAddress;
	private String userName;
	private String externalLogin;
	private Map<String, String> request;
	
	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getExternalLogin() {
		return externalLogin;
	}

	public void setExternalLogin(String externalLogin) {
		this.externalLogin = externalLogin;
	}

	public Map<String, String> getRequest() {
		return request;
	}

	public void setRequest(Map<String, String> request) {
		this.request = request;
	}
	
	@Override
	void populateLinks(Map<String, String> resource) {
		setSelf();
		
		String sessionUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + SESSION_LINK + "/{mac_address}")
				.buildAndExpand(macAddress).toUriString();
		addLink(SESSION_LINK+"-close", new EntityLink("POST", sessionUri));
	}
}
