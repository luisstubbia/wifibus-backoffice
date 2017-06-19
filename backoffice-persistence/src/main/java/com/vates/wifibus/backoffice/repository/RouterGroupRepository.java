package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.RouterGroup;

/**
 * 
 * @author Luis Stubbia
 *
 */
public interface RouterGroupRepository extends JpaRepository<RouterGroup, Long> {

	Optional<RouterGroup> findOneByName(String groupName);
	
	@Modifying
	@Transactional
	@Query("update RouterGroup r set r.name = ?1, r.descripcion = ?2 where r.id = ?3")
	void setRouterGroupInfoByNameAndDescription(String name, String descripcion, Long routerGroupId);
	
	Page<RouterGroup> findByNameContainsAllIgnoreCase(String routerGroupName, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update RouterGroup r set r.enabled = false where r.id = ?1")
	void deleteById(Long routerGroupId);
	
	Long countByName(String name);
	
	List<RouterGroup> findByEnabledTrueOrderByNameDesc();
}
