package com.vates.wifibus.backoffice.validator;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.Router;
import com.vates.wifibus.backoffice.model.RouterForm;
import com.vates.wifibus.backoffice.service.RouterService;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Component
public class RouterFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(RouterFormValidator.class);

	@Autowired
	private RouterService routerService; 
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(RouterForm.class);
	}

	@Override
	public void validate(Object router, Errors errors) {
        logger.debug("Validando {}", router);
        int numberOfOccurrences = 0;
        RouterForm form = (RouterForm) router;
        validateAddress(errors, form);
        validateLocation(errors, form);
		if(form.getId() != null){
        	numberOfOccurrences ++;
        	validateGroupChange(errors, form);
        }
        validateUnique(errors, form, numberOfOccurrences);
        //general validations:
        if (null == form.getGroup()) {
			errors.rejectValue("group", "routerForm.required.group", "Debe indicar el Grupo al que pertenece el Router");
		}
        if (StringUtils.isEmpty(form.getName())){
        	errors.rejectValue("name", "routerForm.required.name", "El nombre del Router es requerido");
        } else {
        	validateName(errors, form, numberOfOccurrences);
        }
	}

	private void validateName(Errors errors, RouterForm form, int numberOfOccurrences) {
		Long numberOfDuplicatedNames = routerService.countByName(form.getName());
		if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
			errors.rejectValue("name", "routerForm.required.name", "El nombre del Router ya existe");
		}
	}

	private void validateGroupChange(Errors errors, RouterForm form) {
		// Forces to update router name when the group has changed -
		Optional<Router> originalRouterInfo = routerService.getById(form.getId());
		if(null != originalRouterInfo && form.getGroup().getId().intValue() != originalRouterInfo.get().getGroup().getId().intValue()){
			if(form.getName().equals(originalRouterInfo.get().getName())){
				errors.rejectValue("name", "routerForm.required.name", "Debe modificar el nombre del Router si esta actualizando el grupo del mismo");
			}
		}
	}

	private void validateUnique(Errors errors, RouterForm form, int numberOfOccurrences) {
		Long duplicatedMacAddressNbr = routerService.countByMacAddress(form.getMacAddress());
		if(null != duplicatedMacAddressNbr && duplicatedMacAddressNbr.intValue() > numberOfOccurrences){
			errors.rejectValue("macAddress", "routerForm.required.macAddress", "La direccion MAC ingresada ya existe");
		}
		Long duplicatedIpAddressNbr = routerService.countByIpAddress(form.getIpV4Address());
		if(null != duplicatedIpAddressNbr && duplicatedIpAddressNbr.intValue() > numberOfOccurrences){
			errors.rejectValue("ipV4Address", "routerForm.required.ipV4Address", "La direccion IP ingresada ya existe");
		}
	}

	private void validateAddress(Errors errors, RouterForm form) {
		if (StringUtils.isEmpty(form.getMacAddress())) {
			errors.rejectValue("macaddress", "routerForm.required.macaddress", "MAC Address es requerida");
		}
		if (StringUtils.isEmpty(form.getIpV4Address())) {
			errors.rejectValue("ipv4address", "routerForm.required.ipv4address", "IP v4 Address es requerida");
		}
	}

	private void validateLocation(Errors errors, RouterForm form) {
		if (StringUtils.isEmpty(form.getLocation())) {
			errors.rejectValue("location", "routerForm.required.location", "La ubicación es requerida");
		}
		if (StringUtils.isEmpty(form.getLatitude())) {
			errors.rejectValue("latitude", "routerForm.required.latitude", "Latitud es requerida");
		}
		if (StringUtils.isEmpty(form.getLongitude())) {
			errors.rejectValue("longitude", "routerForm.required.longitude", "Longitud es requerida");
		}
	}
	
}

