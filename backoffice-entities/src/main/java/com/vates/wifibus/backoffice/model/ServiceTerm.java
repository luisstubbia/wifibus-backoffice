package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Para almacenar el texto de los terminos y condiciones del servicio.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Inheritance
@Table(name = "SERVICE_TERMS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ServiceTerm extends BaseEntity {

	@Column(name = "TEXT", nullable = false)
	@NotNull
	private String text;
	
}
