package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.BannerAd;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.CampaignForm;
import com.vates.wifibus.backoffice.model.VideoAd;
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
        Campaign originalCampaign = null;
        int numberOfOccurrences = 0;
        CampaignForm form = (CampaignForm) campaignForm;
        if(form.getId() != null){
        	numberOfOccurrences ++;
        	originalCampaign = campaignService.getById(form.getId()).get();
        }
        validateName(errors, form, numberOfOccurrences, originalCampaign);
        validateAdvertisement(errors, form);
	}

	private void validateAdvertisement(Errors errors, CampaignForm form) {
		if(CollectionUtils.isEmpty(form.getAdvertisements())){
			errors.rejectValue("advertisements", "campaignForm.required.advertisementForms", "La campaña debe tener al menos un Anuncio");
		} else {
			for(Advertisement adv : form.getAdvertisements()){
				if (StringUtils.isEmpty(adv.getName())) {
					errors.rejectValue("advertisements", "advertisementForm.required.name", "Anuncio(" +adv.getPriority()+ ") - El nombre del Anuncio es requerido");
				}
				if (adv.getStartDate() == null) {
					errors.rejectValue("advertisements", "advertisementForm.required.startDate", "Anuncio(" +adv.getPriority()+ ") - La fecha de unicio del anuncio es requerida");
				}
				if (adv.getEndDate() != null && adv.getStartDate() != null && adv.getEndDate().compareTo(adv.getStartDate()) < 0) {
					errors.rejectValue("advertisements", "advertisementForm.required.endDate", "Anuncio(" +adv.getPriority()+ ") - La fecha de fin del anuncio no puede ser inferior a la fecha de unicio");
				}
				if (adv.getDuration() == null || adv.getDuration().intValue() <= 0) {
					errors.rejectValue("advertisements", "advertisementForm.required.duration", "Anuncio(" +adv.getPriority()+ ") - La duración del anuncio es requerida y debe ser mayor a 0");
				}
				if (adv.getSegment() == null) {
					errors.rejectValue("advertisements", "advertisementForm.required.segment", "Anuncio(" +adv.getPriority()+ ") - Debe estar asociado a un segmento");
				}
				if(adv instanceof VideoAd){
					validateVideoAd(errors, (VideoAd) adv);
				} else if (adv instanceof BannerAd){
					validateBannerAd(errors, (BannerAd) adv);
				}
			}
		}
	}

	private void validateBannerAd(Errors errors, BannerAd adv) {
		if (StringUtils.isEmpty(adv.getBackgroundImageUrl())) {
			errors.rejectValue("advertisements", "advertisementForm.required.backgroundImageUrl", "Anuncio(" +adv.getPriority()+ ") - El Url para la imagen de fondo del banner es requerida");
		}
		if (StringUtils.isEmpty(adv.getText())) {
			errors.rejectValue("advertisements", "advertisementForm.required.text", "Anuncio(" +adv.getPriority()+ ") - El Texto del banner es requerido");
		}
	}

	private void validateVideoAd(Errors errors, VideoAd adv) {
		if (StringUtils.isEmpty(adv.getVideoUrl())) {
			errors.rejectValue("advertisements", "advertisementForm.required.videoUrl", "Anuncio(" +adv.getPriority()+ ") - El Url del video es requerido");
		}
	}

	private void validateName(Errors errors, CampaignForm form, int numberOfOccurrences, Campaign originalCampaign) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "campaignForm.required.name", "El nombre de la campaña es requerido");
		} else {
			if(originalCampaign != null && !originalCampaign.getName().equals(form.getName())){
				numberOfOccurrences --;
			}
			Long numberOfDuplicatedNames = campaignService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "campaignForm.required.name", "El nombre de la campaña ya existe");
			}
		}
	}
}
