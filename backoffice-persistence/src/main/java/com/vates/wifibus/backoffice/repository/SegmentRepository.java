package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.Segment;

/**
 * Repository: Terms and conditions repository.
 * 
 * @author Luis Stubbia
 *
 */
public interface SegmentRepository extends JpaRepository<Segment, Long> {
	
	Optional<Segment> findOneByName(String name);

	@Modifying
	@Transactional
	@Query("update Segment s set s.name = ?1, s.descripcion = ?2 where s.id = ?3")
	void setSegmentInfoByNameAndDescription(String name, String descripcion, Long termsId);
	
	Page<Segment> findByNameContainsAllIgnoreCase(String name, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update Segment s set s.enabled = false where s.id = ?1")
	void deleteById(Long termsId);
	
	Long countByName(String name);
	
	List<Segment> findByEnabledTrueOrderByNameDesc();
	
	@Query("select s from Segment s where s.id in (select a.segment.id from Advertisement a where a.campaign.id = ?1)") 
	List<Segment> getSegmentByCampaign(Long campaignId);
}

