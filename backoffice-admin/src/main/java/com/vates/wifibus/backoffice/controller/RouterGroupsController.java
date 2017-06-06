package com.vates.wifibus.backoffice.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.model.RouterGroup;
import com.vates.wifibus.backoffice.model.RouterGroupForm;
import com.vates.wifibus.backoffice.service.BrandService;
import com.vates.wifibus.backoffice.service.CampaignService;
import com.vates.wifibus.backoffice.service.RouterGroupService;
import com.vates.wifibus.backoffice.service.ServiceTermService;
import com.vates.wifibus.backoffice.validator.RouterGroupFormValidator;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Controller
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
    	return "createOrUpdateGroupForm";
    }

	@RequestMapping(value = "/groups/{groupId}/edit", method = RequestMethod.GET)
    public String getRouterGroupInfoPage(@PathVariable("groupId") Long groupId, Model model) {
    	RouterGroupForm routerGroupForm = new RouterGroupForm();
    	if(null != groupId){
    		Optional<RouterGroup> routerGroup = groupService.getById(groupId);
    		if(routerGroup.isPresent()){
    			BeanUtils.copyProperties(routerGroup.get(), routerGroupForm);
    		}
    	}
    	model.addAttribute(routerGroupForm);
    	addFormDetails(model);
    	return "createOrUpdateGroupForm";
    }

    @RequestMapping(value = {"/groups/{groupId}/edit", "/groups/new"}, method = RequestMethod.POST)
    public String createRouterGroupInfoPage(RouterGroupForm routerGroupForm, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, SessionStatus status) {
        if(action.equals("cancel")){
        	return "redirect:/groups";
        }
        groupValidator.validate(routerGroupForm, result);
        if (result.hasErrors()) {
        	addFormDetails(model);
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
		model.addAttribute("campaigns", campaignService.getAll());
		//model.addAttribute("questionList", questionService.getAll());
	}
}