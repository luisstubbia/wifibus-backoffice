package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Modela entidades que poseen atributos de textos de etiquetas.
 * 
 * @author Gaston Napoli
 *
 */
@MappedSuperclass
@Getter
@Setter
public abstract class LabeledEntity extends BaseEntity {
	
	@Column(name = "LABEL", nullable = false, unique = false, length = 100)
	@NotNull
	private String label;

}
