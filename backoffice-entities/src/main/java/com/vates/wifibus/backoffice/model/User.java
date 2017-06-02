package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Usuario de la aplicacion.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User extends AbstractEntity {

	@Column(name = "USERNAME", nullable = false, unique = true, length = 50)
	@NotNull
	private String username;

	@Column(name = "PASSWORD_HASH", length = 256)
	private String passwordHash;
	
	@Column(name = "FIRST_NAME", nullable = false, length = 50)
	@NotNull
	private String firstName;

	@Column(name = "MIDDLE_NAME", length = 50)
	private String middleName;

	@Column(name = "LAST_NAME", length = 50)
	private String lastName;

	@Column(name = "ENABLED", nullable = false)
	@NotNull
	private boolean enabled = true;

	@Column(name = "OBSERVATIONS", length = 1000)
	private String observations;
	
	@Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

	public User() {
		this(null);
	}

	public User(String username) {
		this(username,null, null);
	}

	public User(String username, String firstName, String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = Role.USER;
	}

	/**
	 * @return
	 */
	public String getFullName() {
		return new StringBuilder(firstName).append(lastName == null ? "" : new StringBuilder(" ").append(lastName))
				.toString();	
	}
	
	@Override
	public String toString() {
		return String.format("User [id=%d, username='%s', role='%s']%n", this.getId(), username, role);
	}

}
