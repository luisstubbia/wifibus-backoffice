package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.Question;

/**
 * 
 * @author Gaston Napoli
 *
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

	Optional<Question> findOneByName(String name);
	
	Page<Question> findByNameContainsAllIgnoreCase(String questionName, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update Question q set q.enabled = false where q.id = ?1")
	void deleteById(Long questionId);
	
	Long countByName(String name);
	
	List<Question> findByEnabledTrueOrderByNameDesc();
}
