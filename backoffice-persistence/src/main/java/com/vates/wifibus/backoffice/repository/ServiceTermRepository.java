package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.ServiceTerm;

/**
 * Repository: Terms and conditions repository.
 * 
 * @author Luis Stubbia
 *
 */
public interface ServiceTermRepository extends JpaRepository<ServiceTerm, Long> {
	
	Optional<ServiceTerm> findOneByName(String name);

	@Modifying
	@Transactional
	@Query("update ServiceTerm s set s.name = ?1, s.descripcion = ?2, s.text = ?3 where s.id = ?4")
	void setTermsInfoByNameAndDescriptionAndText(String name, String descripcion, String text,
			Long termsId);
	
	Page<ServiceTerm> findByNameContainsAllIgnoreCase(String name, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update ServiceTerm s set s.enabled = false where s.id = ?1")
	void deleteById(Long termsId);
	
	Long countByName(String name);

	List<ServiceTerm> findByEnabledTrueOrderByNameDesc();
}

