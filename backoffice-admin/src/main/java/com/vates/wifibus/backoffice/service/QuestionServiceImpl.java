package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vates.wifibus.backoffice.model.Answer;
import com.vates.wifibus.backoffice.model.Question;
import com.vates.wifibus.backoffice.model.QuestionForm;
import com.vates.wifibus.backoffice.repository.QuestionRepository;

/**
 * Service: Question service, used to handle Question info.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Optional<Question> getById(long id) {
		return Optional.ofNullable(questionRepository.findOne(id));
	}

	@Override
	public Optional<Question> getByName(String questionName) {
    	return questionRepository.findOneByName(questionName);
	}
	
	@Override
	public Collection<Question> getAll() {
		return questionRepository.findByEnabledTrueOrderByNameDesc();
	}

	@Override
	public Question save(Question brand) {
		return questionRepository.save(brand);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting Question by id={}", id);
		questionRepository.deleteById(id);
	}

	@Override
	public Page<Question> getQuestions(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return questionRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	public void addOrUpdateQuestion(QuestionForm questionForm) {
		Question question = new Question();
		if(questionForm.getId() != null){
			question = questionRepository.getOne(questionForm.getId());
		}
		mergeItems(question, questionForm);
        BeanUtils.copyProperties(questionForm, question, "id", "answers");
        questionRepository.save(question);
	}

	private void mergeItems(final Question question, QuestionForm questionForm) {
		questionForm.getAnswers().forEach(item->item.setQuestion(question));
		if(!CollectionUtils.isEmpty(question.getAnswers())){
			question.getAnswers().forEach(item->item.setQuestion(null));
			// Adding new items
			questionForm.getAnswers().forEach(item->{
				if(item.getId() == null)
					question.getAnswers().add(item);
			});
			// Removing and updating items that already exist.
			Iterator<Answer> it = question.getAnswers().iterator();
			while(it.hasNext()){
				boolean exist = false;
				Answer ans = (Answer) it.next();
				for(Answer ansForm : questionForm.getAnswers()){
					if(ans.getId() != null && ansForm.getId() != null && ans.getId().intValue() == ansForm.getId().intValue()){
						BeanUtils.copyProperties(ansForm, ans, "id");
						exist = true;
						break;
					}
				}
				if(!exist && ans.getId() != null){
					it.remove();
				}
			}
		} else if(!CollectionUtils.isEmpty(questionForm.getAnswers())){
				question.setAnswers(new LinkedHashSet<Answer>(questionForm.getAnswers()));
		}
	}

	@Override
	public Long countByName(String name) {
		return questionRepository.countByName(name);
	}
	
}