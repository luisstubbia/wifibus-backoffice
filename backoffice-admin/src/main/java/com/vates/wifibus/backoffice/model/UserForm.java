package com.vates.wifibus.backoffice.model;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * DTO del form de edicion de usuarios.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
