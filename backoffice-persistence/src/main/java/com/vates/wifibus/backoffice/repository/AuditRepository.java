package com.vates.wifibus.backoffice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vates.wifibus.backoffice.model.Audit;

/**
 * Repository: Audit repository.
 * 
 * @author Luis Stubbia
 *
 */
public interface AuditRepository extends JpaRepository<Audit, Long> {
	
	Optional<Audit> findOneByEntity(String entityName);

}
