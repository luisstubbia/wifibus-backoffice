package com.vates.wifibus.backoffice.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.model.User;
import com.vates.wifibus.backoffice.model.UserForm;
import com.vates.wifibus.backoffice.service.UserService;
import com.vates.wifibus.backoffice.validator.UserFormValidator;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Controller
@SessionAttributes(types = UserForm.class)
public class UsersController {
	
    @Autowired
    private UserService userService;

	@Autowired
	private UserFormValidator userFormValidator;

	@InitBinder("userForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(userFormValidator);
	}
	
    @RequestMapping("/users")
    public String getUsersPage(Model model) {
    	Page<User> users = userService.getUsers(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(users));
    	model.addAttribute("selections", users.getContent());
    	return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String findUsersPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = false;
    	}
    	Page<User> users = userService.getUsers(paginator.getSelectedPage(), paginator.getSelectedRowsToShow(), 
    			paginator.getQuery());
    	if(users != null && users.getContent().size() == 1 && isQuery){
    		return "redirect:/users/"+ users.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(users);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", users.getContent());
        	return "users";
    	}
    }
    
    @RequestMapping("/users/new")
	public ModelAndView getUserNewPage() {
    	ModelAndView model = new ModelAndView("createOrUpdateUserForm");
    	model.addObject("userForm", new UserForm());
    	return model;
    }
    
	@RequestMapping(value = "/users/{id}/edit", method = RequestMethod.GET)
	public ModelAndView getUserEditPage(@PathVariable Long id) {
   	     ModelAndView modelAndView = new ModelAndView("createOrUpdateUserForm");
   	     User user = userService.getById(id)
 				.orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id)));
   	     UserForm userForm = new UserForm();
   	     BeanUtils.copyProperties(user, userForm);
         modelAndView.addObject("userForm", userForm);
   	     return modelAndView;
	}
    
	@RequestMapping(value = {"/users/{id}/edit", "/users/new"}, method = RequestMethod.POST)
	public String saveNewUserPage(UserForm userForm, BindingResult result, Model model,
			@ModelAttribute("action") String action, SessionStatus status) {
    	userFormValidator.validate(userForm, result);
    	if(result.hasErrors()){
    		return "createOrUpdateUserForm";
    	} else {
    		userService.AddOrUpdate(userForm);
    		status.setComplete();
    		return "redirect:/users";
    	}
	}

	@RequestMapping(value = "/users/{id}/delete", method = RequestMethod.GET)
    public String deleteUserPage(@PathVariable Long id) {
    	userService.deleteById(id);
    	return "redirect:/users";
    }
	
}
