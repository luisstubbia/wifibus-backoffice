package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.RouterGroupForm;
import com.vates.wifibus.backoffice.service.RouterGroupService;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Component
public class RouterGroupFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(RouterGroupFormValidator.class);

	@Autowired
	private RouterGroupService routerGroupService; 
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(RouterGroupForm.class);
	}

	@Override
	public void validate(Object routerGroupForm, Errors errors) {
        logger.debug("Validando {}", routerGroupForm);
        int numberOfOccurrences = 0;
        RouterGroupForm form = (RouterGroupForm) routerGroupForm;
        if(form.getId() != null){
        	numberOfOccurrences++;
        }
        validateName(errors, form, numberOfOccurrences);
	}

	private void validateName(Errors errors, RouterGroupForm form, int numberOfOccurrences) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "routerGroupForm.required.name", "El nombre del Grupo es requerido");
		} else {
			Long numberOfDuplicatedNames = routerGroupService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "routerForm.required.name", "El nombre del Grupo ya existe");
			}
		}
		if (StringUtils.isEmpty(form.getDescripcion())) {
			errors.rejectValue("descripcion", "routerGroupForm.required.descripcion", "La Descripcion es requerida");
		}
	}

}

