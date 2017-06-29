package com.vates.wifibus.backoffice.api.resource;

import java.util.Set;

import com.vates.wifibus.backoffice.model.ButtonType;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;

/**
 * Solicitud de perfil de usuario.
 * 
 * @author luis.stubbia
 *
 */
public class ProfileResponse extends BaseResource<Profile> {

	private Long profileId;
	private String macAddress;
	private String userName;
	private ButtonType loginSource;
	private Set<ProfileValue> values;

	public ProfileResponse(){

	}
	
	public ProfileResponse(Profile profile) {
		profileId = profile.getId();
		macAddress = profile.getMacAddress();
		userName = profile.getUsername();
		loginSource = profile.getLoginSource();
		values = profile.getValues();
	}
	
	public Long getProfileId() {
		return profileId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public String getUserName() {
		return userName;
	}

	public ButtonType getLoginSource() {
		return loginSource;
	}

	public Set<ProfileValue> getValues() {
		return values;
	}
	
	@Override
	void populateLinks(Profile resource) {
		setSelf(null);
	}
}
