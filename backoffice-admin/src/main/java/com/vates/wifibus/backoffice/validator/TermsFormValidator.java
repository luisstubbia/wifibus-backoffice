package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.TermsForm;
import com.vates.wifibus.backoffice.service.ServiceTermService;

/**
 * 
 * @author Luis Stubbia
 * 
 */
@Component
public class TermsFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(TermsFormValidator.class);

	@Autowired
	private ServiceTermService termService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(TermsForm.class);
	}

	@Override
	public void validate(Object termsForm, Errors errors) {
        logger.debug("Validando {}", termsForm);
        int numberOfOccurrences = 0;
        TermsForm form = (TermsForm) termsForm;
        if(form.getId() != null){
        	numberOfOccurrences ++;
        }
        validateName(errors, form, numberOfOccurrences);
	}

	private void validateName(Errors errors, TermsForm form, int numberOfOccurrences) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "termsForm.required.name", "El nombre del Término y condiciones es requerido");
		} else {
			Long numberOfDuplicatedNames = termService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "termsForm.required.name", "El nombre del Termino y condiciones ya existe");
			}
		}
		if (StringUtils.isEmpty(form.getText())) {
			errors.rejectValue("text", "termsForm.required.cobrand", "El Texto del termino y condiciones es requerido");
		}
	}
}
