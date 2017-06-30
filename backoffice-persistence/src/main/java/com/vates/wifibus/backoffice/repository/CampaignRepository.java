package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.Campaign;

/**
 * 
 * @author Gaston Napoli
 *
 */
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

	Optional<Campaign> findOneByName(String name);
	
	Page<Campaign> findByNameContainsAllIgnoreCase(String campaignName, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update Campaign c set c.enabled = false where c.id = ?1")
	void deleteById(Long campaignId);
	
	Long countByName(String name);
	
	List<Campaign> findByEnabledTrueOrderByNameDesc();
	
	@Query("select c from Campaign c, Advertisement a where a.campaign.id = ?1 and c.id = ?1 and a.segment.id in (?2)")
	Optional<Campaign> getFilerAdvertisementByCampaign(Long campaignId, List<Long> ids);
}
