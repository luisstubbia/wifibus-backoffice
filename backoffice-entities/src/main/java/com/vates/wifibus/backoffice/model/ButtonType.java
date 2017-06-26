package com.vates.wifibus.backoffice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Tipos de botones.
 * 
 * @author Luis Stubbia
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ButtonType {
	GOOGLE(1, "google", "Google+"),
	FACEBOOK(2, "facebook", "Facebook"),
	MAIL(3, "envelope", "Email");
	
	private long id;
	private String type;
	private String displayName;

	ButtonType(long id, String type, String displayName){
		this.id = id;
		this.type = type;
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Returns the Button Type that matches with the Id.
	 *
	 * @param id
	 * @return ButtonType
	 */
	public static ButtonType lookupById(Long id) {
		if (id != null) {
			for (ButtonType b : ButtonType.values()) {
				if (id.intValue() == b.getId().intValue()) {
					return b;
		        }
		    }
	    }
	    return null;
	}
}
