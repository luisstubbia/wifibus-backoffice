package com.vates.wifibus.backoffice.api.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.vates.wifibus.backoffice.model.Answer;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;
import com.vates.wifibus.backoffice.model.Question;

/**
 * Get profile value using facebook mapper
 * 
 * @author luis.stubbia
 *
 */
public class FacebookProfileMapper extends ProfileMapper {
	
	@Override
	public ProfileValue getValue(Question question, Profile profile) {
		if(!CollectionUtils.isEmpty(question.getProperties())){
			for(String property : question.getProperties()){
				String prop = property.split("\\.")[1];
				for(ProfileValue answer : profile.getValues()){
					if(answer.getKey().equals(prop)){
						return setAnswerValue(answer, question.getAnswers(), prop);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Set profile value. For question with multiple closed answer, this method uses the
	 * propertiesMapper, defined into app.properties.
	 * 
	 * @param answer
	 * @param answers
	 * @param prop
	 * @return ProfileValue
	 */
	private ProfileValue setAnswerValue(ProfileValue answer, Set<Answer> answers, String prop) {
		if(!CollectionUtils.isEmpty(answers)){
			for (Map.Entry<String, List<String>> entry : getApp().getPropertiesMapper().entrySet()){
				if(entry.getKey().contains(prop)){
					for(Answer ans : answers){
						for(String ansMapper : entry.getValue()){
							if(ans.getName().toLowerCase().contains(ansMapper)){
								answer.setValue(String.valueOf(ans.getValue()));
								return answer;
							}
						}
					}
				}
			}
		}
		return answer;
	}
}