package com.vates.wifibus.backoffice.api.service;

import com.vates.wifibus.backoffice.api.util.ServiceException;
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
	Profile getProfile(Long profileId) throws ServiceException;

	/**
	 * Guarda o actualiza la informacion de un profile
	 * @param profile
	 * @return
	 */
	Profile addOrUpdateProfile(Profile profile) throws ServiceException;
}
