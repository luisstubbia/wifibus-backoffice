package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.RouterGroup;
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
        RouterGroup originalGroup = null;
        RouterGroupForm form = (RouterGroupForm) routerGroupForm;
        if(form.getId() != null){
        	numberOfOccurrences++;
        	originalGroup = routerGroupService.getById(form.getId()).get();
        }
        validateName(errors, form, numberOfOccurrences, originalGroup);
        validateItems(errors, form);
	}

	private void validateItems(Errors errors, RouterGroupForm form) {
		if(form.getBrand() == null){
			errors.rejectValue("brand", "routerGroupForm.required.brand", "Debe seleccionar un Branding");
		}
		if(form.getTermAndCondition() == null){
			errors.rejectValue("termAndCondition", "routerGroupForm.required.termAndCondition", "Debe seleccionar un Termino y condición");
		}
		if(form.getCampaign() == null){
			errors.rejectValue("campaign", "routerGroupForm.required.campaign", "Debe seleccionar una Campaña");
		}
		if(CollectionUtils.isEmpty(form.getButtons())){
			errors.rejectValue("buttons", "routerGroupForm.required.buttons", "Debe seleccionar al menos un boton");
		}
		if(CollectionUtils.isEmpty(form.getQuestions())){
			errors.rejectValue("questions", "routerGroupForm.required.questions", "Debe seleccionar al menos una pregunta");
		}
	}

	private void validateName(Errors errors, RouterGroupForm form, int numberOfOccurrences, RouterGroup originalGroup) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "routerGroupForm.required.name", "El nombre del Grupo es requerido");
		} else {
			if(originalGroup != null && !originalGroup.getName().equals(form.getName())){
				numberOfOccurrences --;
			}
			Long numberOfDuplicatedNames = routerGroupService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "routerForm.required.name", "El nombre del Grupo ya existe");
			}
		}
	}

}

