package com.vates.wifibus.backoffice.api.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo de entidades de tipo link.
 * 
 * @author luis.stubbia
 *
 */
public class EntityLink implements Serializable {

	private static final long serialVersionUID = -3889940609872261132L;
	private String action;
	private String absoluteUri;

	public EntityLink(String action, String absoluteUri) {
		this.action = action;
		this.absoluteUri = absoluteUri;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@JsonProperty("url")
	public String getAbsoluteUri() {
		return absoluteUri;
	}

	public void setAbsoluteUri(String absoluteUri) {
		this.absoluteUri = absoluteUri;
	}
}
