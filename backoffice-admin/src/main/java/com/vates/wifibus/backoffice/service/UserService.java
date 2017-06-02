package com.vates.wifibus.backoffice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.User;
import com.vates.wifibus.backoffice.model.UserForm;

/**
 *
 * @author Luis Stubbia
 *
 */
public interface UserService extends EntityService<User> {

	/**
	 * Get user by username
	 * @param username
	 * @return User
	 */
    Optional<User> getByUsername(String username);
    
    /**
     * Create a new user.
     * @param form
     * @return
     */
    void AddOrUpdate(UserForm form);
    
    /**
     * Get users into a page wrapper
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<User>
     */
	Page<User> getUsers(Integer pageNumber, Integer pageSize, String searchText);
	
	/**
	 * Returns the duplicated username already persisted.
	 * @param username
	 * @return
	 */
	Long countByUsername(String username);

}
