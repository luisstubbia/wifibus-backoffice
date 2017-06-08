package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.Brand;
import com.vates.wifibus.backoffice.model.BrandForm;
import com.vates.wifibus.backoffice.service.BrandService;

/**
 * 
 * @author Luis Stubbia
 * 
 */
@Component
public class BrandFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(BrandFormValidator.class);

	@Autowired
	private BrandService brandService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(BrandForm.class);
	}

	@Override
	public void validate(Object brandForm, Errors errors) {
        logger.debug("Validando {}", brandForm);
        int numberOfOccurrences = 0;
        Brand originalBrand = null;
        BrandForm form = (BrandForm) brandForm;
        if(form.getId() != null){
        	numberOfOccurrences ++;
        	originalBrand = brandService.getById(form.getId()).get();
        }
        validateName(errors, form, numberOfOccurrences, originalBrand);
	}

	private void validateName(Errors errors, BrandForm form, int numberOfOccurrences, Brand originalBrand) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "brandForm.required.name", "El nombre del Branding es requerido");
		} else {
			if(originalBrand != null && !originalBrand.getName().equals(form.getName())){
				numberOfOccurrences --;
			}
			Long numberOfDuplicatedNames = brandService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "brandForm.required.name", "El nombre del Branding ya existe");
			}
		}
		if (StringUtils.isEmpty(form.getCobrand())) {
			errors.rejectValue("cobrand", "brandForm.required.cobrand", "El cobranding es requerido");
		}
		if (StringUtils.isEmpty(form.getLogoImage())) {
			errors.rejectValue("logoImage", "brandForm.required.logoImage", "La imagen de logo es requerida");
		}
		if (StringUtils.isEmpty(form.getBackgroundImage())) {
			errors.rejectValue("backgroundImage", "brandForm.required.backgroundImage", "La imagen de fondo es requerida");
		}
	}
}
