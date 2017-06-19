package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.User;

/**
 * 
 * @author Luis Stubbia
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findOneByUsername(String username);
	
	@Modifying
	@Transactional
	@Query("update User u set u.firstName = ?1, u.lastName = ?2 where u.id = ?3")
	void setUserInfoById(String firsName, String lastName, Long userId);

	Page<User> findByUsernameContainsOrFirstNameContainsAllIgnoreCase(String username, String firstName, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update User u set u.enabled = false where u.id = ?1")
	void deleteById(Long userId);

	Long countByUsername(String username);

	List<User> findByEnabledTrueOrderByUsernameDesc();
}
