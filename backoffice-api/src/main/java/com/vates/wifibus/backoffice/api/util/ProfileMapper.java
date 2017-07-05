package com.vates.wifibus.backoffice.api.util;

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
	
	public static final ProfileMapper getMapper(Profile profile){
		if(profile.getLoginSource().equals(ButtonType.FACEBOOK)){
			return new FacebookProfileMapper();
		} else {
			return new DefaultProfileMapper();
		}
	}
	
	public abstract ProfileValue getValue(Question question, Profile profile);

}
