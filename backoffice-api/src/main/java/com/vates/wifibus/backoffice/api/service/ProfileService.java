package com.vates.wifibus.backoffice.api.service;

import com.vates.wifibus.backoffice.api.resource.ProfileResponse;
import com.vates.wifibus.backoffice.model.Profile;

/**
 * Servicio para manejar los perfiles de usuarios.
 * 
 * @author luis.stubbia
 *
 */
public interface ProfileService {

	/**
	 * Obtiene un profile de usuario por id
	 * @param profileId
	 * @return
	 */
	ProfileResponse getProfile(Long profileId);

	/**
	 * Guarda o actualiza la informacion de un profile
	 * @param profile
	 * @return
	 */
	ProfileResponse addOrUpdateProfile(Profile profile);
}
