package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.Profile;

/**
 * Repository: Perfil de usuario.
 * 
 * @author Luis Stubbia
 *
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
	Optional<Profile> findOneByUsername(String name);
		
	@Modifying
	@Transactional
	@Query("update Profile p set p.enabled = false where p.id = ?1")
	void deleteById(Long profileId);
	
	Long countByUsername(String name);
	
	@Query("select p from Profile p JOIN p.macAddress mc where mc = ?1")
	List<Profile> getProfileByMacAddress(String macAddress);
}