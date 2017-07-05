package com.vates.wifibus.backoffice.api.util;

import com.vates.wifibus.backoffice.api.config.ApplicationConfiguration;
import com.vates.wifibus.backoffice.model.ButtonType;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;
import com.vates.wifibus.backoffice.model.Question;

/**
 * Get profile value mapper.
 * 
 * @author luis.stubbia
 *
 * @param <ProfileValue>
 */
public abstract class ProfileMapper {
	
	private ApplicationConfiguration app;
	
	public static final ProfileMapper getMapper(Profile profile){
		if(profile.getLoginSource().equals(ButtonType.FACEBOOK)){
			return new FacebookProfileMapper();
		} else {
			return new DefaultProfileMapper();
		}
	}
	
	/**
	 * Get profile value.
	 * 
	 * @param question
	 * @param profile
	 * @return
	 */
	public abstract ProfileValue getValue(Question question, Profile profile);

	public ApplicationConfiguration getApp() {
		return app;
	}

	public void setApp(ApplicationConfiguration app) {
		this.app = app;
	}

}
