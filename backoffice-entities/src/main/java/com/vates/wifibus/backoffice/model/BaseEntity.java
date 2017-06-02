package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Gaston Napoli
 *
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends AbstractEntity {

	@JsonIgnore
	@Column(name = "NAME", nullable = false, unique = true, length = 50)
	@NotNull
	private String name;
	
	@JsonIgnore
	@Column(name = "DESCRIPTION", nullable = true, length = 500)
	private String descripcion;
	
}
