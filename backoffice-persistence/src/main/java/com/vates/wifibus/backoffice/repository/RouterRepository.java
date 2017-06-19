package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.Router;

/**
 * Repository: Group repository.
 * 
 * @author Luis Stubbia
 *
 */
public interface RouterRepository extends JpaRepository<Router, Long> {
	
	Optional<Router> findOneByName(String routerName);

	@Modifying
	@Transactional
	@Query("update Router r set r.name = ?1, r.descripcion = ?2, r.macAddress = ?3, r.ipV4Address = ?4, "
			+ "r.location = ?5, r.latitude = ?6, r.longitude = ?7, r.group.id = ?8  where r.id = ?9")
	void setRouterInfoByNameMacIpLocationDescLatitudeAndLonguitude(String name, String descripcion, String macAddress, 
			String ipV4Address, String location, Double latitude, Double longitude, Long groupId, Long routerId);
	
	Page<Router> findByNameContainsAllIgnoreCase(String routerName, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update Router r set r.enabled = false where r.id = ?1")
	void deleteById(Long routerId);
	
	Long countByMacAddress(String macAddress);
	
	Long countByIpV4Address(String ipV4Address);
	
	Long countByName(String name);
	
	List<Router> findByEnabledTrueOrderByNameDesc();
}