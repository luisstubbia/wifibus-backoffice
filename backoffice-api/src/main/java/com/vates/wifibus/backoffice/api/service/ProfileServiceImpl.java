package com.vates.wifibus.backoffice.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.api.resource.ProfileResponse;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;
import com.vates.wifibus.backoffice.repository.ProfileRepository;

/**
 * 
 * @author luis.stubbia
 *
 */
@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public ProfileResponse getProfile(Long profileId) {
		ProfileResponse prof = new ProfileResponse();
		if(profileId != null){
			Profile profile = profileRepository.findOne(profileId);
			if(profile != null){
				prof = new ProfileResponse(profile);
			} else {
				prof.addError(new BussinesError(ErrorCode.PROFILE_NOT_FOUND));
			}
		} else {
			prof.addError(new BussinesError(ErrorCode.BAD_INPUT));
		}
		return prof;
	}

	@Override
	public ProfileResponse addOrUpdateProfile(Profile profile) {
		ProfileResponse prof = new ProfileResponse();
		validateProfile(profile, prof);
		if(!prof.hasErrors()){
			Profile finalProfile;
			Optional<Profile> dbProfile = profileRepository.findOneByUsername(profile.getUsername());
			if(!dbProfile.isPresent()){
				dbProfile = profileRepository.findOneByMacAddress(profile.getMacAddress());
			}
			if(dbProfile.isPresent()){
				finalProfile = dbProfile.get();
				mergeProfile(profile, finalProfile);
			} else {
				finalProfile = profile;
			}
			profileRepository.save(finalProfile);
			prof = new ProfileResponse(finalProfile);
		}
		return prof;
	}

	/**
	 * Merge keys - values
	 * @param profile
	 * @param finalProfile
	 */
	private void mergeProfile(Profile profile, final Profile finalProfile) {
		profile.getValues().forEach(item->{
			boolean exist = false;
			for(ProfileValue value : finalProfile.getValues()){
				if(value.getKey().equals(item.getKey())){
					value.setValue(item.getValue());
					exist = true;
					break;
				}
			}
			if(!exist){
				item.setProfile(finalProfile);
				finalProfile.getValues().add(item);
			}
		});
	}

	/**
	 * Valida los campos obligatorios en el POST.
	 * 
	 * @param profileReq
	 * @param prof
	 */
	private void validateProfile(Profile profileReq, ProfileResponse prof) {
		if(StringUtils.isEmpty(profileReq.getUsername())){
			prof.addError(new BussinesError(ErrorCode.PROFILE_MISSING_USERNAME));
		}
		if(StringUtils.isEmpty(profileReq.getMacAddress())){
			prof.addError(new BussinesError(ErrorCode.PROFILE_MISSING_MAC_ADRESS));
		}
		if(profileReq.getLoginSource() == null){
			prof.addError(new BussinesError(ErrorCode.PROFILE_MISSING_LOGIN_SOURCE));
		}
		if(CollectionUtils.isEmpty(profileReq.getValues())){
			prof.addError(new BussinesError(ErrorCode.PROFILE_MISSING_VALUES));
		}
	}
}