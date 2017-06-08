package com.vates.wifibus.backoffice.model;

/**
 * Tipos de preguntas.
 * 
 * @author Gaston Napoli
 *
 */
public enum QuestionType {

    TEXT("Texto", true),
    NUMBER("Numerico", true),
    CALENDAR("Calendario", true),
    DROPDOWN("Lista", false);
	
	private String displayName;
	
	private boolean isOpen;
	
	QuestionType(String displayName, boolean isOpen){
		this.setDisplayName(displayName);
		this.setOpen(isOpen);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	/**
	 * Returns the Question Type that matches the displayName.
	 *
	 * @param displayName
	 * @return QuestionType
	 */
	public static QuestionType lookupByName(String name) {
		if (name != null) {
			for (QuestionType b : QuestionType.values()) {
				if (name.equalsIgnoreCase(b.name())) {
					return b;
		        }
		    }
	    }
	    return null;
	}
	
}
