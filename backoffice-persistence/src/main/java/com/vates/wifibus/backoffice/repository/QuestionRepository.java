package com.vates.wifibus.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vates.wifibus.backoffice.model.Question;

/**
 * 
 * @author Gaston Napoli
 *
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
