package com.vates.wifibus.api.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vates.wifibus.backoffice.model.RouterGroup;
import com.vates.wifibus.backoffice.repository.RouterGroupRepository;

/**
 * 
 * @author Gaston Napoli
 *
 */
@RepositoryRestResource
public interface RouterGroupRestRepository extends RouterGroupRepository {

   RouterGroup findByRoutersHotspotsName(@Param("name")String name);
    
}
