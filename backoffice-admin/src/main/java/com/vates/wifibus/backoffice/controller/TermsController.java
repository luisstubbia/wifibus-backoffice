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
import org.springframework.web.servlet.ModelAndView;

import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.model.ServiceTerm;
import com.vates.wifibus.backoffice.model.TermsForm;
import com.vates.wifibus.backoffice.service.ServiceTermService;
import com.vates.wifibus.backoffice.validator.TermsFormValidator;

@Controller
@SessionAttributes(types = TermsForm.class)
public class TermsController {

	@Autowired
	private ServiceTermService termsService;

	@Autowired
	private TermsFormValidator termsValidator;
	
    @GetMapping("/terms")
    public String getTermsPage(Model model) {
    	Page<ServiceTerm> terms = termsService.getTerms(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(terms));
    	model.addAttribute("selections", terms.getContent());
        return "terms";
    }
    
    @RequestMapping(value = "/terms", method = RequestMethod.POST)
    public String findTermsPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = true;
    	}
    	Page<ServiceTerm> terms = termsService.getTerms(paginator.getSelectedPage(), 
    			paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if(terms != null && terms.getContent().size() == 1 && isQuery){
    		return "redirect:/terms/"+ terms.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(terms);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", terms.getContent());
        	return "terms";
    	}
    }
    
    @RequestMapping("/terms/new")
	public ModelAndView getTermNewPage() {
		ModelAndView model = new ModelAndView("createOrUpdateTermsForm");
		TermsForm termsForm = new TermsForm();
		model.addObject("termsForm", termsForm);
    	return model;
    }
    
    @RequestMapping(value = "/terms/{termsId}/edit", method = RequestMethod.GET)
    public String getBrandInfoPage(@PathVariable("termsId") Long termsId, Model model) {
    	TermsForm termsForm = new TermsForm();
    	if(null != termsId){
    		Optional<ServiceTerm> term = termsService.getById(termsId);
    		if(term.isPresent()){
    			BeanUtils.copyProperties(term.get(), termsForm);
    		}
    	}
    	model.addAttribute(termsForm);
    	return "createOrUpdateTermsForm";
    }
    
    @RequestMapping(value = {"/terms/{termsId}/edit", "/terms/new"}, method = RequestMethod.POST)
    public String createTermsInfoPage(TermsForm term, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, SessionStatus status) {
        termsValidator.validate(term, result);
        if (result.hasErrors()) {
            return "createOrUpdateTermsForm";
        } else {
        	termsService.addOrUpdateTerms(term);
        	status.setComplete();
            return "redirect:/terms";
        }
    }
    
	@RequestMapping(value = "/terms/{id}/delete", method = RequestMethod.GET)
    public String deleteTermsPage(@PathVariable Long id) {
    	termsService.deleteById(id);
    	return "redirect:/terms";
    }
}