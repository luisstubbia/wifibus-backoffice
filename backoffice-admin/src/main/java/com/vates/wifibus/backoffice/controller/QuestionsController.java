package com.vates.wifibus.backoffice.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vates.wifibus.backoffice.model.AnswerForm;
import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.model.Question;
import com.vates.wifibus.backoffice.model.QuestionForm;
import com.vates.wifibus.backoffice.model.QuestionType;
import com.vates.wifibus.backoffice.service.QuestionService;
import com.vates.wifibus.backoffice.validator.QuestionFormValidator;
/**
 * 
 * @author Gaston Napoli
 *
 */
@Controller
@SessionAttributes(types = QuestionForm.class)
public class QuestionsController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionFormValidator questionValidator;
	
    @GetMapping("/questions")
    public String getQuestionsPage(Model model) {
    	Page<Question> questions = questionService.getQuestions(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(questions));
    	model.addAttribute("selections", questions.getContent());
        return "questions";
    }
    
    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public String findQuestionsPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = true;
    	}
    	Page<Question> questions = questionService.getQuestions(paginator.getSelectedPage(), 
    			paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if(questions != null && questions.getContent().size() == 1 && isQuery){
    		return "redirect:/questions/"+ questions.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(questions);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", questions.getContent());
        	return "questions";
    	}
    }
    
    @RequestMapping("/questions/new")
	public String getQuestionNewPage(Model model) {
    	QuestionForm questionForm = new QuestionForm();
		model.addAttribute("questionForm", questionForm);
    	return "createOrUpdateQuestionForm";
    }
    
    @RequestMapping(value = "/questions/{questionId}/edit", method = RequestMethod.GET)
    public String getQuestionInfoPage(@PathVariable("questionId") Long questionId, Model model) {
    	QuestionForm questionForm = new QuestionForm();
    	if(null != questionId){
    		Optional<Question> question = questionService.getById(questionId);
    		if(question.isPresent()){
    			BeanUtils.copyProperties(question.get(), questionForm);
    			questionForm.buildAnswers(question.get().getAnswers());
    		}
    	}
    	model.addAttribute(questionForm);
    	return "createOrUpdateQuestionForm";
    }
    
    @RequestMapping(value = {"/questions/{questionId}/edit", "/questions/new"}, method = RequestMethod.POST)
    public String createQuestionInfoPage(QuestionForm question, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, @ModelAttribute("removeAnw") String anwId,  
    		SessionStatus status) {
        if(!StringUtils.isEmpty(anwId)){
        	removeAnw(question, Integer.parseInt(anwId));
        	return "createOrUpdateQuestionForm";
        }
        QuestionType type = QuestionType.lookupByName(question.getType().name());
        question.setOpen(type.isOpen());
        question.setType(type);
        questionValidator.validate(question, result);
        if (result.hasErrors()) {
            return "createOrUpdateQuestionForm";
        } else {
        	questionService.addOrUpdateQuestion(question);
        	status.setComplete();
            return "redirect:/questions";
        }
    }

	@RequestMapping(value = {"/questions/{questionId}/edit", "/questions/new"}, method = RequestMethod.POST, params="action=addAnw")
    public String addAnswerView(QuestionForm question, BindingResult result, Model model, SessionStatus status) {
		QuestionType type = QuestionType.lookupByName(question.getType().name());
		if(!type.isOpen()){
    		question.addAnswerForm();
    	}
		return "createOrUpdateQuestionForm";
    }
    
	@RequestMapping(value = "/questions/{id}/delete", method = RequestMethod.GET)
    public String deleteBrandPage(@PathVariable Long id) {
		questionService.deleteById(id);
    	return "redirect:/questions";
    }
	
    
    /**
     * Remove answer element from question form.
     * @param 
     * @param advId
     */
    private void removeAnw(QuestionForm question, int anwId) {
		for(AnswerForm anw : question.getAnswerForms()){
			if(anw.getIndex() == anwId){
				if(anw.getId() != null){
					//Remove element form db?
				}
				question.getAnswerForms().remove(anw);
				break;
			}
		}
    }
}
