package com.vates.wifibus.backoffice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.CampaignForm;
import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.model.SegmentForm;
import com.vates.wifibus.backoffice.model.SegmentItem;
import com.vates.wifibus.backoffice.service.SegmentService;

/**
 * 
 * @author Luis Stubbia
 * 
 */
@Component
public class SegmentFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(SegmentFormValidator.class);

	@Autowired
	private SegmentService segmentService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CampaignForm.class);
	}

	@Override
	public void validate(Object segmentForm, Errors errors) {
        logger.debug("Validando {}", segmentForm);
        Segment originalSegment = null;
        int numberOfOccurrences = 0;
        SegmentForm form = (SegmentForm) segmentForm;
        if(form.getId() != null){
        	numberOfOccurrences ++;
        	originalSegment = segmentService.getById(form.getId()).get();
        }
        validateName(errors, form, numberOfOccurrences, originalSegment);
        validateItems(errors, form);
	}

	private void validateItems(Errors errors, SegmentForm form) {
		if(CollectionUtils.isEmpty(form.getItems())){
			errors.rejectValue("items", "segmentForm.required.items", "El segmento debe tener al menos una regla");
		} else {
			for(SegmentItem itm : form.getItems()){
				if (itm.getQuestion() == null) {
					errors.rejectValue("items", "segmentForm.required.question", "Regla(" +itm.getIndex()+ ") - La regla debe indicar cual es la pregunta");
				}
				if (itm.getOperator() == null) {
					errors.rejectValue("items", "segmentForm.required.operator", "Regla(" +itm.getIndex()+ ") - La regla debe tener asociada un operador");
				}
				if (StringUtils.isEmpty(itm.getValue())) {
					errors.rejectValue("items", "segmentForm.required.value", "Regla(" +itm.getIndex()+ ") - La regla debe definr el valor de la respuesta");
				}
			}
		}
	}

	private void validateName(Errors errors, SegmentForm form, int numberOfOccurrences, Segment originalSegment) {
		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "segmentForm.required.name", "El nombre del segmento es requerido");
		} else {
			if(originalSegment != null && !originalSegment.getName().equals(form.getName())){
				numberOfOccurrences --;
			}
			Long numberOfDuplicatedNames = segmentService.countByName(form.getName());
			if(null != numberOfDuplicatedNames && numberOfDuplicatedNames.intValue() > numberOfOccurrences){
				errors.rejectValue("name", "segmentForm.required.name", "El nombre del segmento ya existe");
			}
		}
	}
}
