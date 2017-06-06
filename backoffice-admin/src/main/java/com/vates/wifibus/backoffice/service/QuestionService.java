package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.Question;
import com.vates.wifibus.backoffice.model.QuestionForm;

/**
 * Service: branding service
 * 
 * @author Luis Stubbia
 *
 */
public interface QuestionService extends EntityService<Question> {
	
    /**
     * Get Questions into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<Question>
     */
    Page<Question> getQuestions(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a Question.
     * @param brand form
     */
	void addOrUpdateQuestion(QuestionForm question);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}