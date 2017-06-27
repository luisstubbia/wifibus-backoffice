package com.vates.wifibus.backoffice.api.resource;

import java.util.Map;

/**
 * Recurso de configuraciones.
 * 
 * @author luis.stubbia
 *
 */
public class Config {
	
	private Map<String,String> apiKeys;

	public Map<String,String> getApiKeys() {
		return apiKeys;
	}

	public void setApiKeys(Map<String,String> apiKeys) {
		this.apiKeys = apiKeys;
	}
}
