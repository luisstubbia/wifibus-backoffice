package com.vates.wifibus.backoffice.api.util;

import com.vates.wifibus.backoffice.model.SegmentItem;

public class DROPDOWNBuilder extends QuestionBuilder {

	@Override
	public boolean validAnswer(SegmentItem item, String value) {
		switch (item.getOperator()) {
			case EQUAL:
				return validateEqual(item.getValue(), value);
			default:
				return false;
		}
	}

	private boolean validateEqual(String expectedValue, String actualValue) {
		if(expectedValue.equals(actualValue))
			return true;

		return false;
	}
}
