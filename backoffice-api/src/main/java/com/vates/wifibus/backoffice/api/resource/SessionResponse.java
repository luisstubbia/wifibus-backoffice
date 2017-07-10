package com.vates.wifibus.backoffice.api.resource;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vates.wifibus.backoffice.model.Profile;

/**
 * Maneja las sesiones de las conecciones de usuarios por router.
 * 
 * @author luis.stubbia
 *
 */
public class SessionResponse extends BaseResource{

	private String macAddress;
	private Long profileId;
	private String username;
	private Date logDate;

	public SessionResponse() {

	}

	public SessionResponse(Profile profile, String macAddress) {
		this.macAddress = macAddress;
		this.profileId = profile.getId();
		this.username = profile.getUsername();
		this.logDate = new Date();
	}

	public String getMacAddress() {
		return macAddress;
	}

	public Long getProfileId() {
		return profileId;
	}

	public String getUsername() {
		return username;
	}

	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getLogDate() {
		return logDate;
	}
}
