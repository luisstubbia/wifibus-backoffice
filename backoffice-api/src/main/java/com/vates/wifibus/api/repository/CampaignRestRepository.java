package com.vates.wifibus.api.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vates.wifibus.backoffice.repository.CampaignRepository;

/**
 * 
 * @author Gaston Napoli
 *
 */
@RepositoryRestResource
public interface CampaignRestRepository extends CampaignRepository {

}
