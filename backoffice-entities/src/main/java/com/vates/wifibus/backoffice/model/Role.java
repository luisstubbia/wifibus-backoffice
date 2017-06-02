package com.vates.wifibus.backoffice.model;

/**
 * Roles en la aplicacion.
 * 
 * @author Gaston Napoli
 *
 */
public enum Role {
    
    USER("Usuario"), 
    ADMIN("Administrador");
	
	private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
