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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.vates.wifibus.backoffice.model.Hotspot;
import com.vates.wifibus.backoffice.model.HotspotForm;
import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.service.HotspotService;
import com.vates.wifibus.backoffice.service.RouterService;
import com.vates.wifibus.backoffice.validator.HotspotFormValidator;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Controller
public class HotspotsController {

	@Autowired
	private HotspotService hotspotService;

	@Autowired
	private RouterService routerService;

	@Autowired
	private HotspotFormValidator hotspotValidator;
	
    @GetMapping("/hotspots")
    public String getHotspotsPage(Model model) {
    	Page<Hotspot> hotspots = hotspotService.getHotspots(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(hotspots));
    	model.addAttribute("selections", hotspots.getContent());
        return "hotspots";
    }
    
    @RequestMapping(value = "/hotspots", method = RequestMethod.POST)
    public String findHotspotPage(PaginatorForm paginator, BindingResult result, Model model) {
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    	}
    	Page<Hotspot> hotspots = hotspotService.getHotspots(paginator.getSelectedPage(), 
    			paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if(hotspots != null && hotspots.getContent().size() == 1){
    		return "redirect:/hotspots/"+ hotspots.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(hotspots);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", hotspots.getContent());
        	return "hotspots";
    	}
    }
    
    @RequestMapping("/hotspots/new")
	public ModelAndView getHotspotNewPage() {
		ModelAndView model = new ModelAndView("createOrUpdateHotspotForm");
		model.addObject("routers", routerService.getAll());
		HotspotForm hotspotForm = new HotspotForm();
		model.addObject("hotspotForm", hotspotForm);
    	return model;
    }
    
    @RequestMapping(value = "/hotspots/{hotspotId}/edit", method = RequestMethod.GET)
    public String getHotspotInfoPage(@PathVariable("hotspotId") Long hotspotId, Model model) {
    	HotspotForm hotspotForm = new HotspotForm();
    	if(null != hotspotId){
    		Optional<Hotspot> hotspot = hotspotService.getById(hotspotId);
    		if(hotspot.isPresent()){
    			BeanUtils.copyProperties(hotspot.get(), hotspotForm);
    		}
    	}
    	model.addAttribute(hotspotForm);
    	model.addAttribute("routers", routerService.getAll());
    	return "createOrUpdateHotspotForm";
    }
    
    @RequestMapping(value = {"/hotspots/{hotspotId}/edit", "/hotspots/new"}, method = RequestMethod.POST)
    public String createHotspotInfoPage(HotspotForm hotspot, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, SessionStatus status) {
        if(action.equals("cancel")){
        	return "redirect:/hotspots";
        }
        hotspotValidator.validate(hotspot, result);
        if (result.hasErrors()) {
        	model.addAttribute("routers", routerService.getAll());
            return "createOrUpdateHotspotForm";
        } else {
        	hotspotService.addOrUpdateHotspot(hotspot);
        	status.setComplete();
            return "redirect:/hotspots";
        }
    }
    
	@RequestMapping(value = "/hotspots/{id}/delete", method = RequestMethod.GET)
    public String deleteHotspotPage(@PathVariable Long id) {
    	hotspotService.deleteById(id);
    	return "redirect:/hotspots";
    }
	
}
