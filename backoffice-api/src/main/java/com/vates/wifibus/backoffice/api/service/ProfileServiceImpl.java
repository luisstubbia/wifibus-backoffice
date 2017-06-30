package com.vates.wifibus.backoffice.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.api.util.ServiceException;
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
	public Profile getProfile(Long profileId) throws ServiceException {
		if(profileId != null){
			Profile profile = profileRepository.findOne(profileId);
			if(profile != null){
				return profile;
			} else {
				new ServiceException(new BussinesError(ErrorCode.PROFILE_NOT_FOUND));
			}
		} else {
			new ServiceException(new BussinesError(ErrorCode.BAD_INPUT));
		}
		return null;
	}

	@Override
	public Profile addOrUpdateProfile(Profile profile) throws ServiceException {
		validateProfile(profile);
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
		return finalProfile;
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
	private void validateProfile(Profile profileReq) throws ServiceException {
		List<BussinesError> errors = new ArrayList<BussinesError>();
		if(StringUtils.isEmpty(profileReq.getUsername())){
			errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_USERNAME));
		}
		if(StringUtils.isEmpty(profileReq.getMacAddress())){
			errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_MAC_ADRESS));
		}
		if(profileReq.getLoginSource() == null){
			errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_LOGIN_SOURCE));
		}
		if(CollectionUtils.isEmpty(profileReq.getValues())){
			errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_VALUES));
		}
		if(!CollectionUtils.isEmpty(errors)){
			new ServiceException(errors);
		}
	}
}