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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.model.Router;
import com.vates.wifibus.backoffice.model.RouterForm;
import com.vates.wifibus.backoffice.service.RouterGroupService;
import com.vates.wifibus.backoffice.service.RouterService;
import com.vates.wifibus.backoffice.validator.RouterFormValidator;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Controller
@SessionAttributes(types = RouterForm.class)
public class RoutersController {

    @Autowired
    private RouterService routerService;
    
    @Autowired
    private RouterGroupService routerGroupService;
    
    @Autowired
    private RouterFormValidator routerValidator;
	
    @RequestMapping(value = "/routers", method = RequestMethod.GET)
    public String getRoutersPage(Model model) {
    	Page<Router> routers = routerService.getRouters(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(routers));
    	model.addAttribute("selections", routers.getContent());
        return "routers";
    }
    
    @RequestMapping(value = "/routers", method = RequestMethod.POST)
    public String findRoutersPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = false;
    	}
    	Page<Router> routers = routerService.getRouters(paginator.getSelectedPage(), paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if(routers != null && routers.getContent().size() == 1 && isQuery){
    		return "redirect:/routers/"+ routers.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(routers);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", routers.getContent());
        	return "routers";
    	}
    }

	@RequestMapping("/routers/new")
	public ModelAndView getRouterNewPage() {
		ModelAndView model = new ModelAndView("createOrUpdateRouterForm");
		model.addObject("groups", routerGroupService.getAll());
		RouterForm routerForm = new RouterForm();
		model.addObject("routerForm", routerForm);
    	return model;
    }
	
    @RequestMapping(value = "/routers/{routerId}/edit", method = RequestMethod.GET)
    public String getRouterInfoPage(@PathVariable("routerId") Long routerId, Model model) {
    	RouterForm routerForm = new RouterForm();
    	if(null != routerId){
    		Optional<Router> router = routerService.getById(routerId);
    		if(router.isPresent()){
    			BeanUtils.copyProperties(router.get(), routerForm);
    			routerForm.setIpv4address(router.get().getIpV4Address());
    		}
    	}
    	model.addAttribute(routerForm);
    	model.addAttribute("groups", routerGroupService.getAll());
    	return "createOrUpdateRouterForm";
    }

    @RequestMapping(value = {"/routers/new", "/routers/{routerId}/edit"}, method = RequestMethod.POST)
    public String createRouterInfoPage(RouterForm router, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, SessionStatus status) {
        routerValidator.validate(router, result);
        if (result.hasErrors()) {
        	model.addAttribute("groups", routerGroupService.getAll());
            return "createOrUpdateRouterForm";
        } else {
        	routerService.addOrUpdateRouter(router);
        	status.setComplete();
            return "redirect:/routers";
        }
    }
	
	@RequestMapping(value = "/routers/{id}/delete", method = RequestMethod.GET)
    public String deleteRouterPage(@PathVariable Long id) {
    	routerService.deleteById(id);
    	return "redirect:/routers";
    }
}
