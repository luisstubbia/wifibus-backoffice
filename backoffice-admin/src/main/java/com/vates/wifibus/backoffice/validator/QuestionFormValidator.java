package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.Answer;
import com.vates.wifibus.backoffice.model.Question;
import com.vates.wifibus.backoffice.model.QuestionForm;
import com.vates.wifibus.backoffice.service.QuestionService;

/**
 * 
 * @author Luis Stubbia
 * 
 */
@Component
public class QuestionFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(QuestionFormValidator.class);

	@Autowired
	private QuestionService questionService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(QuestionForm.class);
	}

	@Override
	public void validate(Object questionForm, Errors errors) {
        logger.debug("Validando {}", questionForm);
        int numberOfOccurrences = 0;
        Question originalQuestion = null;
        QuestionForm form = (QuestionForm) questionForm;
        if(form.getId() != null){
        	numberOfOccurrences ++;
        	originalQuestion = questionService.getById(form.getId()).get();
        }
        validateName(errors, form, numberOfOccurrences, originalQuestion);
        validateAnswers(errors, form);
	}

	private void validateAnswers(Errors errors, QuestionForm form) {
		if(!form.getType().isOpen()){
			if(CollectionUtils.isEmpty(form.getAnswers())){
				errors.rejectValue("answers", "questionForm.required.answers", "La pregunta debe tener al menos una respuesta");
			} else {
				int index = 0;
				for(Answer anw : form.getAnswers()){
					index++;
					if (StringUtils.isEmpty(anw.getName())) {
						errors.rejectValue("answers", "answerForm.required.name", "Respuesta(" +index+ ") - El nombre de la respuesta es requerido");
					}
					if (StringUtils.isEmpty(anw.getLabel())) {
						errors.rejectValue("answers", "answerForm.required.label", "Respuesta(" +index+ ") - La titulo de la respuesta es requerido");
					}
					if (anw.getValue() == null) {
						errors.rejectValue("answers", "answerForm.required.value", "Respuesta(" +index+ ") - La valor de la respuesta debe ser numerico");
					}
				}
			}
		}
	}

	private void validateName(Errors errors, QuestionForm form, int numberOfOccurrences, Question originalQuestion) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "questionForm.required.name", "El nombre de la pregunta es requerido");
		} else {
			if(originalQuestion != null && !originalQuestion.getName().equals(form.getName())){
				numberOfOccurrences --;
			}
			Long numberOfDuplicatedNames = questionService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "questionForm.required.name", "El nombre de la pregunta ya existe");
			}
		}
		if (StringUtils.isEmpty(form.getLabel())) {
			errors.rejectValue("label", "questionForm.required.label", "El titulo de la pregunta es requerido");
		}
	}
}
