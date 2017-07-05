package com.vates.wifibus.backoffice.api.util;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.vates.wifibus.backoffice.model.SegmentItem;

public class NUMBERBuilder extends QuestionBuilder {
	
	@Override
	public boolean validAnswer(SegmentItem item, String value) {
		try {
			Long expectedValue = Long.parseLong(item.getValue());
			Long actualValue = Long.MIN_VALUE;
			//the actualValue can be a date..
			try {
				DATE_FORMATTER.parse(value);
				actualValue = Integer.toUnsignedLong(CALENDARBuilder.CALCULATE_AGE(LocalDate.parse(value, DATE_FORMATTER)));
			} catch (DateTimeParseException e) {
				actualValue = Long.parseLong(value);
			}
			switch (item.getOperator()) {
				case EQUAL:
					return validateEqual(expectedValue, actualValue);
				case GREATER_THAN:
					return validateGreaterThan(expectedValue, actualValue);
				case SMALLER_THAN:
					return validateSmallerThan(expectedValue, actualValue);
				case EQUAL_GREATER_THAN:
					return validateEqualGreaterThan(expectedValue, actualValue);
				case EQUAL_SMALLER_THAN:
					return validateEqualSmallerThan(expectedValue, actualValue);
				default:
					return false;
			}
		} catch(NumberFormatException ex) {
			logger.error("Invalid number format: {}" + ex);
			return false;
		}
	}
	
	private boolean validateEqual(Long expectedValue, Long actualValue) {
		if(expectedValue.intValue() == actualValue.intValue())
			return true;
	
		return false;
	}
	
	private boolean validateGreaterThan(Long expectedValue, Long actualValue) {
		if(expectedValue.intValue() < actualValue.intValue())
			return true;
	
		return false;
	}
	
	private boolean validateSmallerThan(Long expectedValue, Long actualValue) {
		if(expectedValue.intValue() > actualValue.intValue())
			return true;
	
		return false;
	}
	
	private boolean validateEqualGreaterThan(Long expectedValue, Long actualValue) {
		if(expectedValue.intValue() <= actualValue.intValue())
			return true;
	
		return false;
	}
	
	private boolean validateEqualSmallerThan(Long expectedValue, Long actualValue) {
		if(expectedValue.intValue() >= actualValue.intValue())
			return true;
	
		return false;
	}
}
