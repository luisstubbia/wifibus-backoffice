package com.vates.wifibus.backoffice.api.resource;

import java.util.Map;

/**
 * Solicitud de perfil de usuario.
 * 
 * @author luis.stubbia
 *
 */
public class Filter {

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
}
