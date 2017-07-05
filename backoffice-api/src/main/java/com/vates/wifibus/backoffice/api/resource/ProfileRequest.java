package com.vates.wifibus.backoffice.api.resource;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.vates.wifibus.backoffice.model.ButtonType;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;

/**
 * Solicitud de perfil de usuario.
 * 
 * @author luis.stubbia
 *
 */
public class ProfileRequest {

	private String macAddress;
	private String username;
	private ButtonType loginSource;
	private Set<ProfileValue> values;

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ButtonType getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(ButtonType loginSource) {
		this.loginSource = loginSource;
	}

	public Set<ProfileValue> getValues() {
		return values;
	}

	public void setValues(Set<ProfileValue> values) {
		this.values = values;
	}
	
	public Profile toModel() {
		Profile profile = new Profile();
		BeanUtils.copyProperties(this, profile, "macAddress");
		profile.getValues().forEach(item->item.setProfile(profile));
		LinkedHashSet<String> macSet = new LinkedHashSet<String>(1);
		macSet.add(macAddress);
		profile.setMacAddress(macSet);
		return profile;
	}
}
