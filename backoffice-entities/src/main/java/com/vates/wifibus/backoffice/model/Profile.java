package com.vates.wifibus.backoffice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Perfil de Usuario de la aplicación.
 * 
 * @author Luis Stubbia
 *
 */
@Entity
@Table(name = "PROFILES")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Profile extends AbstractEntity {

	@Column(name = "USERNAME", nullable = false, unique = true, length = 50)
	@NotNull
	private String username;

	@Column(name = "MAC_ADDRESS", nullable = false, length = 17)
	@NotNull
	private String macAddress;
	
	@Column(name = "LOGIN_SOURCE", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private ButtonType loginSource;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profile", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<ProfileValue> values;
}
