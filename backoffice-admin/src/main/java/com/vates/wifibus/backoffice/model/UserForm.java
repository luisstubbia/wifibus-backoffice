package com.vates.wifibus.backoffice.model;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * 
 * DTO del form de edicion de usuarios.
 * 
 * @author Luis Stubbia
 *
 */
@Data
public class UserForm {

	@NotEmpty
	private Long id;
	
    @NotEmpty
    private String username = "";

    @NotEmpty
    private String firstName = "";
    
    @NotEmpty
    private String lastName = "";
 
    @NotEmpty
    private String password = "";

    @NotEmpty
    private String passwordRepeated = "";
    
    @NotEmpty
    private boolean enabled = true;

    private String observations = "";
    
    @NotEmpty
    private Role role = Role.USER;

}
