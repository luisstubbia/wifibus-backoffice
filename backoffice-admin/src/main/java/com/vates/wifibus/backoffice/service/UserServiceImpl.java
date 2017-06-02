package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.User;
import com.vates.wifibus.backoffice.model.UserForm;
import com.vates.wifibus.backoffice.repository.UserRepository;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getById(long id) {
    	logger.debug("Getting user={}", id);
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getByUsername(String username) {
    	Optional<User> user = userRepository.findOneByUsername(username);
    	logger.debug("Getting user by username={}, {}", username, user);	
    	return user;
    }

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll(new Sort("username"));
    }

    @Override
    public void AddOrUpdate(UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        if(user.getId() != null){
        	logger.info("Updating");
        	userRepository.setUserInfoById(userForm.getFirstName(), userForm.getLastName(), userForm.getId());
        } else {
        	logger.info("Nuevo usuario");
        	user.setPasswordHash(passwordEncoder.encode(userForm.getPassword()));
        	userRepository.save(user);
        }
    }

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting user by id={}", id);
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> getByName(String name) {
		return getByUsername(name);
	}

	@Override
	public Page<User> getUsers(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "username");
		return userRepository.findByUsernameContainsOrFirstNameContainsAllIgnoreCase(searchText, searchText, pageRequest);
	}

	@Override
	public Long countByUsername(String username) {
		return userRepository.countByUsername(username);
	}
	
}