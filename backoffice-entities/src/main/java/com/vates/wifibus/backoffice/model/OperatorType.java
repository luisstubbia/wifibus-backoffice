package com.vates.wifibus.backoffice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tipos de operadores.
 * 
 * @author Luis Stubbia
 *
 */
public enum OperatorType {

    EQUAL("=", "{x} igual a: {y}", QuestionType.CALENDAR, QuestionType.NUMBER, QuestionType.TEXT, QuestionType.DROPDOWN),
    GREATER_THAN(">", "{x} mayor a: {y}", QuestionType.CALENDAR, QuestionType.NUMBER),
    SMALLER_THAN("<", "{x} menor a: {y}", QuestionType.CALENDAR, QuestionType.NUMBER),
    EQUAL_GREATER_THAN(">=", "{x} mayor o igual a: {y}", QuestionType.CALENDAR, QuestionType.NUMBER),
	EQUAL_SMALLER_THAN("<=", "{x} menor o igual a: {y}", QuestionType.CALENDAR, QuestionType.NUMBER),
	AGE_EQUAL("Años =", "fecha(x) igual a: {Años}", QuestionType.CALENDAR, QuestionType.NUMBER),
	AGE_GREATER_THAN("Años >", "fecha(x) mayor a: {Años}", QuestionType.CALENDAR, QuestionType.NUMBER),
	AGE_SMALLER_THAN("Años <", "fecha(x) menor a: {Años}", QuestionType.CALENDAR, QuestionType.NUMBER);
	
	private String displayName;
	private String description;
	private QuestionType[] questionTypes;
	
	OperatorType(String displayName, String description, QuestionType... questionTypes){
		this.displayName = displayName;
		this.setDescription(description);
		this.questionTypes = questionTypes;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	/**
	 * Returns the Operation Type that matches the displayName.
	 *
	 * @param displayName
	 * @return OperationType
	 */
	public static List<OperatorType> lookupByQuestionType(QuestionType type) {
		List<OperatorType> ots = new ArrayList<OperatorType>();
		if (type != null) {
			for (OperatorType b : OperatorType.values()) {
				for(QuestionType qt : b.getQuestionTypes()){
					if(qt.equals(type)){
						ots.add(b);
					}
				}
		    }
	    }
	    return ots;
	}	
}
