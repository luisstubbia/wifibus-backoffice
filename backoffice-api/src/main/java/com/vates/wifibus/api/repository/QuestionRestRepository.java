package com.vates.wifibus.api.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vates.wifibus.backoffice.repository.QuestionRepository;

/**
 * 
 * @author Gaston Napoli
 *
 */
@RepositoryRestResource
public interface QuestionRestRepository extends QuestionRepository {

}
