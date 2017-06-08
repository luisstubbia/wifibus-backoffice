package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.Answer;
import com.vates.wifibus.backoffice.model.AnswerForm;
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
		return questionRepository.findAll(new Sort("name"));
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
        BeanUtils.copyProperties(questionForm, question);
        copyAnswerProperties(questionForm, question);
        questionRepository.save(question);
	}

	private void copyAnswerProperties(QuestionForm questionForm, Question question) {
		if(!questionForm.getType().isOpen()){
			Set<Answer> answers = new HashSet<Answer>();
			for(AnswerForm answerForm : questionForm.getAnswerForms()){
				Answer answer = new Answer();
				BeanUtils.copyProperties(answerForm, answer);
				answer.setQuestion(question);
				answers.add(answer);
			}
			question.setAnswers(answers);
		}
	}

	@Override
	public Long countByName(String name) {
		return questionRepository.countByName(name);
	}
	
}