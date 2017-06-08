package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.User;
import com.vates.wifibus.backoffice.model.UserForm;
import com.vates.wifibus.backoffice.service.UserService;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Component
public class UserFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(UserFormValidator.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
        logger.debug("Validando {}", target);
        UserForm userForm = (UserForm) target;
        User originalUser = null;
        int numberOfOccurrences = 0;
        if(userForm.getId() == null){
        	validatePasswords(errors, userForm);
        } else {
        	numberOfOccurrences ++;
        	originalUser = userService.getById(userForm.getId()).get();
        }
        validateUsername(errors, userForm, numberOfOccurrences, originalUser);
        validateName(errors, userForm);
	}

	private void validatePasswords(Errors errors, UserForm userForm) {
		if (!userForm.getPassword().equals(userForm.getPasswordRepeated())) {
			errors.rejectValue("password","userForm.password.no_match", "Claves no coinciden");
		}
	}

	private void validateUsername(Errors errors, UserForm form, int numberOfOccurrences, User originalUser) {
		if(originalUser != null && !originalUser.getUsername().equals(form.getUsername())){
			numberOfOccurrences --;
		}
		Long duplicatedNbr = userService.countByUsername(form.getUsername());
		if (null != duplicatedNbr && duplicatedNbr.intValue() > numberOfOccurrences) {
			errors.rejectValue("username", "userForm.username.already_exists", "Ya existe un usuario con este nombre");
		}
	}
	
	private void validateName(Errors errors, UserForm form) {
		if (StringUtils.isEmpty(form.getLastName())) {
			errors.rejectValue("lastName", "userForm.lastName.required", "El apellido es requerido");
		}
		if (StringUtils.isEmpty(form.getFirstName())) {
			errors.rejectValue("firstName", "userForm.firstName.required", "El Nombre es requerido");
		}
	}
}

