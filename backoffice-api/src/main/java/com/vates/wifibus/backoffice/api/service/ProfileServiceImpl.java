package com.vates.wifibus.backoffice.api.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

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

	@Transactional
	@Override
	public Profile addOrUpdateProfile(Profile profile) throws ServiceException {
		validateProfile(profile);
		Profile finalProfile;
		String mac = profile.getMacAddress().iterator().next();
		cleanDuplicatedMacAddress(mac);
		Optional<Profile> dbProfile = profileRepository.findOneByUsername(profile.getUsername());
		if(dbProfile.isPresent()){
			finalProfile = dbProfile.get();
			mergeProfile(profile, finalProfile);
		} else {
			finalProfile = profile;
		}
		finalProfile.getMacAddress().add(mac);
		profileRepository.save(finalProfile);
		return finalProfile;
	}

	/**
	 * Remove duplicated mac address
	 * @param mac
	 */
	private void cleanDuplicatedMacAddress(String mac) {
		List<Profile> profiles = profileRepository.getProfileByMacAddress(mac);
		if(!CollectionUtils.isEmpty(profiles)){
			for(Profile prof : profiles){
				Iterator<String> macIt = prof.getMacAddress().iterator();
				while(macIt.hasNext()){
					if(macIt.next().equals(mac)){
						macIt.remove();
					}
				}
			}
			profileRepository.save(profiles);
		}
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
		if(CollectionUtils.isEmpty(profileReq.getMacAddress())){
			errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_MAC_ADRESS));
		} else {
			for(String mac : profileReq.getMacAddress()){
				List<String> error = new ArrayList<String>();
				if(!isValidMACAddress(mac)){
					error.add("MAC invalida: "+ mac);
				}
				if(!CollectionUtils.isEmpty(error)){
					errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_MAC_ADRESS, error));
				}
			}
		}
		if(profileReq.getLoginSource() == null){
			errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_LOGIN_SOURCE));
		}
		if(CollectionUtils.isEmpty(profileReq.getValues())){
			errors.add(new BussinesError(ErrorCode.PROFILE_MISSING_VALUES));
		}
		if(!CollectionUtils.isEmpty(errors)){
			throw new ServiceException(errors);
		}
	}
	
	private boolean isValidMACAddress(String mac) {
		Pattern p = Pattern.compile("^([0-9a-fA-F]{2}[:-]){5}[0-9a-fA-F]{2}$");
		Matcher m = p.matcher(mac);
		return m.find();
	}
}