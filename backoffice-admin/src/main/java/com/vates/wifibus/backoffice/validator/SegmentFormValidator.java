package com.vates.wifibus.backoffice.validator;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vates.wifibus.backoffice.model.CampaignForm;
import com.vates.wifibus.backoffice.model.OperatorType;
import com.vates.wifibus.backoffice.model.QuestionType;
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

	private static final List<OperatorType> AGE_TYPES = Arrays.asList(OperatorType.AGE_EQUAL, OperatorType.AGE_GREATER_THAN, OperatorType.AGE_SMALLER_THAN);
	
	protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
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
		if(!CollectionUtils.isEmpty(form.getItems())){
			for(SegmentItem itm : form.getItems()){
				if (itm.getQuestion() == null) {
					errors.rejectValue("items", "segmentForm.required.question", "Regla(" +itm.getIndex()+ ") - La regla debe indicar cual es la pregunta");
				}
				if (itm.getOperator() == null) {
					errors.rejectValue("items", "segmentForm.required.operator", "Regla(" +itm.getIndex()+ ") - La regla debe tener asociada un operador");
				}
				if (StringUtils.isEmpty(itm.getValue())) {
					errors.rejectValue("items", "segmentForm.required.value", "Regla(" +itm.getIndex()+ ") - La regla debe definr el valor de la respuesta");
				} else {
					validateResponse(errors, itm);
				}
			}
		}
	}

	private void validateResponse(Errors errors, SegmentItem itm) {
		if (itm.getQuestion() != null){
			if (itm.getQuestion().getType().equals(QuestionType.CALENDAR)) {
				if(!isValidDate(itm.getValue())){
					if(AGE_TYPES.contains(itm.getOperator())){
						if(!isValidNumber(itm.getValue())){
							errors.rejectValue("items", "segmentForm.required.value", "Regla(" +itm.getIndex()+ ") - El formato de la respuesta es incorrecta - Valor esperado [DD-MM-YYYY] o [Numero]");
						}
					} else {
						errors.rejectValue("items", "segmentForm.required.value", "Regla(" +itm.getIndex()+ ") - El formato de la respuesta [fecha] es incorrecta [DD-MM-YYYY]");
					}
				}
			} 
			if (itm.getQuestion().getType().equals(QuestionType.DROPDOWN)) {
				if(Integer.parseInt(itm.getValue()) == 0){
					errors.rejectValue("items", "segmentForm.required.value", "Regla(" +itm.getIndex()+ ") - Debe seleccionar una respuesta correcta");
				}
			}
			if (itm.getQuestion().getType().equals(QuestionType.NUMBER)) {
				if(!isValidNumber(itm.getValue())) {
					errors.rejectValue("items", "segmentForm.required.value", "Regla(" +itm.getIndex()+ ") - La respuesta ingresada es incorrecta. Valor esperado [Numero]");
				}
			}
		}
	}

	/**
	 * Is it a valid number?
	 * @param value
	 * @return boolean
	 */
	private boolean isValidNumber(String value) {
		try {
			Long.parseLong(value);
		} catch(NumberFormatException longEx) {
			try {
				Integer.parseInt(value);
			} catch(NumberFormatException intEx){
				return false;
			}
		}
		return true;
	}

	/**
	 * Is it a valid date format?
	 * @param value
	 * @return boolean
	 */
	private boolean isValidDate(String value) {
		try {
			DATE_FORMATTER.parse(value);
		} catch (DateTimeParseException e){
			return false;
		}
		return true;
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
