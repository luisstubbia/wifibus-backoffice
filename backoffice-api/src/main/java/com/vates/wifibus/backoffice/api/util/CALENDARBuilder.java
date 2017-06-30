package com.vates.wifibus.backoffice.api.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.vates.wifibus.backoffice.model.OperatorType;
import com.vates.wifibus.backoffice.model.SegmentItem;

public class CALENDARBuilder extends QuestionBuilder {

	private static final List<OperatorType> AGE_TYPES = Arrays.asList(OperatorType.AGE_EQUAL, OperatorType.AGE_GREATER_THAN, OperatorType.AGE_SMALLER_THAN);
	 
	@Override
	public boolean validAnswer(SegmentItem item, String value) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        LocalDate expectedValue = null;
        LocalDate actualValue = null;
		int expectedNbrValue = 0;
		int actualNbrValue = 0;
	
		expectedValue = LocalDate.parse(item.getValue(), formatter);
		if(AGE_TYPES.contains(item.getOperator())){
			actualNbrValue = Integer.parseInt(value);
			expectedNbrValue = calculateAge(expectedValue);
		} else {
			actualValue = LocalDate.parse(value, formatter);
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
			case AGE_EQUAL:
				return validateAgeEqual(expectedNbrValue, actualNbrValue);
			case AGE_GREATER_THAN:
				return validateAgeGreaterThan(expectedNbrValue, actualNbrValue);
			case AGE_SMALLER_THAN:
				return validateAgeSmallerThan(expectedNbrValue, actualNbrValue);
			default:
				return false;
		}
	}

	private boolean validateEqual(LocalDate expectedValue, LocalDate actualValue) {
		if(expectedValue.isEqual(actualValue))
			return true;
		
		return false;
	}

	private boolean validateGreaterThan(LocalDate expectedValue, LocalDate actualValue) {
		if(expectedValue.isAfter(actualValue))
			return true;
		
		return false;
	}

	private boolean validateSmallerThan(LocalDate expectedValue, LocalDate actualValue) {
		if(expectedValue.isBefore(actualValue))
			return true;
		
		return false;
	}

	private boolean validateEqualGreaterThan(LocalDate expectedValue, LocalDate actualValue) {
		if(expectedValue.isAfter(actualValue) || expectedValue.isEqual(actualValue))
			return true;
		
		return false;
	}

	private boolean validateEqualSmallerThan(LocalDate expectedValue, LocalDate actualValue) {
		if(expectedValue.isBefore(actualValue) || expectedValue.isEqual(actualValue))
			return true;
		
		return false;
	}

	/**
	 * fecha(x) igual a: {Años}
	 * 
	 * @param expectedValue
	 * @param actualValue
	 * @return
	 */
	private boolean validateAgeEqual(int expectedValue, int actualValue) {
		if(expectedValue == actualValue)
			return true;
		
		return false;
	}
	
	/**
	 * fecha(x) mayor a: {Años}
	 * 
	 * @param expectedValue
	 * @param actualValue
	 * @return
	 */
	private boolean validateAgeGreaterThan(int expectedValue, int actualValue) {
		if(expectedValue < actualValue)
			return true;
		
		return false;
	}
	
	/**
	 * fecha(x) menor a: {Años}
	 * 
	 * @param expectedValue
	 * @param actualValue
	 * @return
	 */
	private boolean validateAgeSmallerThan(int expectedValue, int actualValue) {
		if(expectedValue > actualValue)
			return true;
		
		return false;
	}
	
	/**
	 * Get age
	 * 
	 * @param birthDate
	 * @return int
	 */
	public int calculateAge(LocalDate birthDate) {
		LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }
}
