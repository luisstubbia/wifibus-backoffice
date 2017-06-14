package com.vates.wifibus.backoffice.model;

/**
 * Tipos de operadores.
 * 
 * @author Luis Stubbia
 *
 */
public enum OperatorType {

    EQUAL("=", QuestionType.CALENDAR, QuestionType.NUMBER, QuestionType.TEXT, QuestionType.DROPDOWN),
    GREATER_THAN(">", QuestionType.CALENDAR, QuestionType.NUMBER),
    SMALLER_THAN("<", QuestionType.CALENDAR, QuestionType.NUMBER),
    EQUAL_GREATER_THAN(">=", QuestionType.CALENDAR, QuestionType.NUMBER),
	EQUAL_SMALLER_THAN("<=", QuestionType.CALENDAR, QuestionType.NUMBER),
	AGE_EQUAL("Años =", QuestionType.CALENDAR, QuestionType.NUMBER),
	AGE_GREATER_THAN("Años >", QuestionType.CALENDAR, QuestionType.NUMBER),
	AGE_SMALLER_THAN("Años <", QuestionType.CALENDAR, QuestionType.NUMBER);
	
	private String displayName;
	
	private QuestionType[] questionTypes;
	
	OperatorType(String displayName, QuestionType... questionTypes){
		this.displayName = displayName;
		this.questionTypes = questionTypes;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public QuestionType[] getQuestionTypes() {
		return questionTypes;
	}

	public void setQuestionTypes(QuestionType[] questionTypes) {
		this.questionTypes = questionTypes;
	}

	/**
	 * Returns the Operation Type that matches the displayName.
	 *
	 * @param displayName
	 * @return OperationType
	 */
	public static OperatorType lookupByName(String name) {
		if (name != null) {
			for (OperatorType b : OperatorType.values()) {
				if (name.equalsIgnoreCase(b.name())) {
					return b;
		        }
		    }
	    }
	    return null;
	}
	
}
