package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.Hotspot;
import com.vates.wifibus.backoffice.model.HotspotForm;
import com.vates.wifibus.backoffice.service.HotspotService;

/**
 * 
 * @author Luis Stubbia
 * 
 */
@Component
public class HotspotFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(HotspotFormValidator.class);

	@Autowired
	private HotspotService hotspotService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(HotspotForm.class);
	}

	@Override
	public void validate(Object hotspotForm, Errors errors) {
        logger.debug("Validando {}", hotspotForm);
        Hotspot originalHotspot = null;
        int numberOfOccurrences = 0;
        HotspotForm form = (HotspotForm) hotspotForm;
        if(form.getId() != null){
        	numberOfOccurrences ++;
        	originalHotspot = hotspotService.getById(form.getId()).get();
        }
        validateName(errors, form, numberOfOccurrences, originalHotspot);
	}

	private void validateName(Errors errors, HotspotForm form, int numberOfOccurrences, Hotspot originalHotspot) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "hotspotForm.required.name", "El nombre del Hotspot es requerido");
		} else {
			if(originalHotspot != null && !originalHotspot.getName().equals(form.getName())){
				numberOfOccurrences --;
			}
			Long numberOfDuplicatedNames = hotspotService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "hotspotForm.required.name", "El nombre del Hotspot ya existe");
			}
		}
		if (null == form.getRouter()) {
			errors.rejectValue("router", "hotspotForm.required.router", "Debe indicar el Router asociado al Hotspot");
		}
	}
	
}
