package com.vates.wifibus.backoffice.api.util;

import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;
import com.vates.wifibus.backoffice.model.Question;

/**
 * Get default profile value mapper
 * 
 * @author luis.stubbia
 *
 */
public class DefaultProfileMapper extends ProfileMapper {

	@Override
	public ProfileValue getValue(Question question, Profile profile) {
		for(ProfileValue answer : profile.getValues()){
			if(question.getName().equals(answer.getKey())){
				return answer;
			}
		}
		return null;
	}

}
