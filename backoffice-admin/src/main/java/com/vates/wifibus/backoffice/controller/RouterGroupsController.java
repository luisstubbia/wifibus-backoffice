package com.vates.wifibus.backoffice.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vates.wifibus.backoffice.model.ButtonType;
import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.model.Question;
import com.vates.wifibus.backoffice.model.RouterGroup;
import com.vates.wifibus.backoffice.model.RouterGroupForm;
import com.vates.wifibus.backoffice.service.BrandService;
import com.vates.wifibus.backoffice.service.CampaignService;
import com.vates.wifibus.backoffice.service.QuestionService;
import com.vates.wifibus.backoffice.service.RouterGroupService;
import com.vates.wifibus.backoffice.service.ServiceTermService;
import com.vates.wifibus.backoffice.validator.RouterGroupFormValidator;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Controller
@SessionAttributes(types = RouterGroupForm.class)
public class RouterGroupsController {
	
	@Autowired
	private RouterGroupService groupService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ServiceTermService termService;

	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private RouterGroupFormValidator groupValidator;
	
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String getGroupsPage(Model model) {
    	Page<RouterGroup> routerGroups = groupService.getRouterGroups(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(routerGroups));
    	model.addAttribute("selections", routerGroups.getContent());
        return "groups";
    }
    
    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public String findRouterGroupsPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if (StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = false;
    	}
    	Page<RouterGroup> routerGroups = groupService.getRouterGroups(paginator.getSelectedPage(), 
    			paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if (routerGroups != null && routerGroups.getContent().size() == 1 && isQuery) {
    		return "redirect:/groups/"+ routerGroups.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(routerGroups);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", routerGroups.getContent());
        	return "groups";
    	}
    }

	@RequestMapping("/groups/new")
	public String getRouterGroupNewPage(Model model) {
		RouterGroupForm routerGroupForm = new RouterGroupForm();
		model.addAttribute("routerGroupForm", routerGroupForm);
		addFormDetails(model);
		updateLinkedList(model, routerGroupForm);
    	return "createOrUpdateGroupForm";
    }

	@RequestMapping(value = "/groups/{groupId}/edit", method = RequestMethod.GET)
    public String getRouterGroupInfoPage(@PathVariable("groupId") Long groupId, Model model) {
    	RouterGroupForm routerGroupForm = new RouterGroupForm();
    	if(null != groupId){
    		Optional<RouterGroup> routerGroup = groupService.getById(groupId);
    		if(routerGroup.isPresent()){
    			RouterGroup group = routerGroup.get();
    			BeanUtils.copyProperties(group, routerGroupForm, "questions");
    			if(!CollectionUtils.isEmpty(group.getQuestions())){
    				routerGroupForm.getQuestions().addAll(group.getQuestions());
    			}
    		}
    	}
    	model.addAttribute(routerGroupForm);
    	addFormDetails(model);
    	updateLinkedList(model, routerGroupForm);
    	return "createOrUpdateGroupForm";
    }

    @RequestMapping(value = {"/groups/{groupId}/edit", "/groups/new"}, method = RequestMethod.POST)
    public String createRouterGroupInfoPage(RouterGroupForm routerGroupForm, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, @ModelAttribute("addQuestion") String addQstId,
    		@ModelAttribute("removeQuestion") String rmQstId, @ModelAttribute("addButton") String addbtnId,
    		@ModelAttribute("removeButton") String rmBtnId, SessionStatus status) {
    	addFormDetails(model);
    	if(!StringUtils.isEmpty(rmQstId)){
        	removeQuestion(routerGroupForm, Long.parseLong(rmQstId));
        	updateLinkedList(model, routerGroupForm);
        	return "createOrUpdateGroupForm";
        }
        if(!StringUtils.isEmpty(addQstId)){
        	addQuestion(routerGroupForm, Long.parseLong(addQstId));
        	updateLinkedList(model, routerGroupForm);
        	return "createOrUpdateGroupForm";
        }
        if(!StringUtils.isEmpty(rmBtnId)){
        	removeButton(routerGroupForm, Long.parseLong(rmBtnId));
        	updateLinkedList(model, routerGroupForm);
        	return "createOrUpdateGroupForm";
        }
        if(!StringUtils.isEmpty(addbtnId)){
        	addButton(routerGroupForm, Long.parseLong(addbtnId));
        	updateLinkedList(model, routerGroupForm);
        	return "createOrUpdateGroupForm";
        }
    	groupValidator.validate(routerGroupForm, result);
        if (result.hasErrors()) {
        	updateLinkedList(model, routerGroupForm);
        	return "createOrUpdateGroupForm";
        } else {
        	groupService.addOrUpdateRouterGroup(routerGroupForm);
        	status.setComplete();
            return "redirect:/groups";
        }
    }

	@RequestMapping(value = "/groups/{id}/delete", method = RequestMethod.GET)
    public String deleteRouterGroupPage(@PathVariable Long id) {
    	groupService.deleteById(id);
    	return "redirect:/groups";
    }
	
    private void addFormDetails(Model model) {
		model.addAttribute("brands", brandService.getAll());
		model.addAttribute("terms", termService.getAll());
		model.addAttribute("campaigns", campaignService.getCampaignsByDefault(false));
		model.addAttribute("defaultCampaigns", campaignService.getCampaignsByDefault(true));
	}
    
    private void updateLinkedList(Model model, RouterGroupForm routerGroupForm){
    	updateButtonList(model, routerGroupForm.getButtons());
    	updateQuestionList(model, routerGroupForm.getQuestions());
    }
    
    private void updateButtonList(Model model, Collection<ButtonType> buttons){
    	List<ButtonType> bts = new ArrayList<ButtonType>(EnumSet.allOf(ButtonType.class));
		bts.removeAll(buttons);
		model.addAttribute("buttonList", bts);
    }
    
    private void updateQuestionList(Model model, Collection<Question> questions){
    	Collection<Question> qts = questionService.getAll();
    	qts.removeAll(questions);
		model.addAttribute("questionList", qts);
    }
    
    // Question operations.
	private void addQuestion(RouterGroupForm routerGroupForm, long questionId) {
		boolean addQuestion = true;
		for( Question q : routerGroupForm.getQuestions()){
			if(q.getId().intValue() == questionId){
				addQuestion = false;
				break;
			}
		}
		if(addQuestion){
			Question question = questionService.getById(questionId).get();
			routerGroupForm.addQuestion(question);
		}
	}

	private void removeQuestion(RouterGroupForm routerGroupForm, long questionId) {
		Iterator<Question> it = routerGroupForm.getQuestions().iterator();
		while(it.hasNext()){
			Question q = it.next();
			if(q.getId().intValue() == questionId){
				it.remove();
			}
		}
	}
	
	// Button operations.
	private void addButton(RouterGroupForm routerGroupForm, long buttonId) {
		boolean addButton = true;
		for( ButtonType b : routerGroupForm.getButtons()){
			if(b.getId().intValue() == buttonId){
				addButton = false;
				break;
			}
		}
		if(addButton){
			ButtonType btn = ButtonType.lookupById(buttonId);
			routerGroupForm.addButton(btn);
		}
	}

	private void removeButton(RouterGroupForm routerGroupForm, long buttonId) {
		Iterator<ButtonType> it = routerGroupForm.getButtons().iterator();
		while(it.hasNext()){
			ButtonType b = it.next();
			if(b.getId().intValue() == buttonId){
				it.remove();
			}
		}
	}
}