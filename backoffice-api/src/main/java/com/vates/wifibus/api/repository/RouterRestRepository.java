package com.vates.wifibus.api.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vates.wifibus.backoffice.model.Router;
import com.vates.wifibus.backoffice.repository.RouterRepository;

/**
 * 
 * @author Gaston Napoli
 *
 */
@RepositoryRestResource
public interface RouterRestRepository extends RouterRepository {

    Router findByHotspots_Name(@Param("name")String name);
    
}
