package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.AdvertisementForm;
import com.vates.wifibus.backoffice.model.BannerAdForm;
import com.vates.wifibus.backoffice.model.CampaignForm;
import com.vates.wifibus.backoffice.model.VideoAdForm;
import com.vates.wifibus.backoffice.service.CampaignService;

/**
 * 
 * @author Luis Stubbia
 * 
 */
@Component
public class CampaignFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(CampaignFormValidator.class);

	@Autowired
	private CampaignService campaignService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CampaignForm.class);
	}

	@Override
	public void validate(Object campaignForm, Errors errors) {
        logger.debug("Validando {}", campaignForm);
        int numberOfOccurrences = 0;
        CampaignForm form = (CampaignForm) campaignForm;
        if(form.getId() != null){
        	numberOfOccurrences ++;
        }
        validateName(errors, form, numberOfOccurrences);
        validateAdvertisement(errors, form);
	}

	private void validateAdvertisement(Errors errors, CampaignForm form) {
		if(CollectionUtils.isEmpty(form.getAdvertisementForms())){
			errors.rejectValue("advertisementForms", "campaignForm.required.advertisementForms", "La campaña debe tener al menos un Anuncio");
		} else {
			for(AdvertisementForm<?,?> advForm : form.getAdvertisementForms()){
				if (StringUtils.isEmpty(advForm.getName())) {
					errors.rejectValue("advertisementForms", "advertisementForm.required.name", "Anuncio(" +advForm.getPriority()+ ") - El nombre del Anuncio es requerido");
				}
				if (advForm.getStartDate() == null) {
					errors.rejectValue("advertisementForms", "advertisementForm.required.startDate", "Anuncio(" +advForm.getPriority()+ ") - La fecha de unicio del anuncio es requerida");
				}
				if (advForm.getEndDate() != null && advForm.getStartDate() != null && advForm.getEndDate().compareTo(advForm.getStartDate()) < 0) {
					errors.rejectValue("advertisementForms", "advertisementForm.required.endDate", "Anuncio(" +advForm.getPriority()+ ") - La fecha de fin del anuncio no puede ser inferior a la fecha de unicio");
				}
				if (advForm.getDuration() == null || advForm.getDuration().intValue() <= 0) {
					errors.rejectValue("advertisementForms", "advertisementForm.required.duration", "Anuncio(" +advForm.getPriority()+ ") - La duración del anuncio es requerida y debe ser mayor a 0");
				}
				if(advForm instanceof VideoAdForm){
					validateVideoAdForm(errors, (VideoAdForm) advForm);
				} else if (advForm instanceof BannerAdForm){
					validateBannerAdForm(errors, (BannerAdForm) advForm);
				}
			}
		}
	}

	private void validateBannerAdForm(Errors errors, BannerAdForm advForm) {
		if (StringUtils.isEmpty(advForm.getBackgroundImageUrl())) {
			errors.rejectValue("advertisementForms", "advertisementForm.required.backgroundImageUrl", "Anuncio(" +advForm.getPriority()+ ") - El Url para la imagen de fondo del banner es requerida");
		}
		if (StringUtils.isEmpty(advForm.getText())) {
			errors.rejectValue("advertisementForms", "advertisementForm.required.text", "Anuncio(" +advForm.getPriority()+ ") - El Texto del banner es requerido");
		}
	}

	private void validateVideoAdForm(Errors errors, VideoAdForm advForm) {
		if (StringUtils.isEmpty(advForm.getVideoUrl())) {
			errors.rejectValue("advertisementForms", "advertisementForm.required.videoUrl", "Anuncio(" +advForm.getPriority()+ ") - El Url del video es requerido");
		}
	}

	private void validateName(Errors errors, CampaignForm form, int numberOfOccurrences) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "campaignForm.required.name", "El nombre de la campaña es requerido");
		} else {
			Long numberOfDuplicatedNames = campaignService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "campaignForm.required.name", "El nombre de la campaña ya existe");
			}
		}
	}
}
