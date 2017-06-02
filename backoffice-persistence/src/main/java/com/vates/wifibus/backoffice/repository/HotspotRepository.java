package com.vates.wifibus.backoffice.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.Hotspot;

/**
 * Repository: Group repository.
 * 
 * @author Luis Stubbia
 *
 */
public interface HotspotRepository extends JpaRepository<Hotspot, Long> {
	
	Optional<Hotspot> findOneByName(String hotspotName);

	@Modifying
	@Transactional
	@Query("update Hotspot h set h.name = ?1, h.descripcion = ?2, h.router.id = ?3 where h.id = ?4")
	void setHotspotInfoByNameAndDescriptionAndRouteId(String name, String descripcion, Long routerId,
			Long hotspotId);
	
	Page<Hotspot> findByNameContainsAllIgnoreCase(String hotspotName, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update Hotspot h set h.enabled = false where h.id = ?1")
	void deleteById(Long hotspotId);
	
	Long countByName(String name);
	
}

