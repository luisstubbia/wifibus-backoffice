package com.vates.wifibus.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vates.wifibus.backoffice.model.Campaign;

/**
 * 
 * @author Gaston Napoli
 *
 */
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
