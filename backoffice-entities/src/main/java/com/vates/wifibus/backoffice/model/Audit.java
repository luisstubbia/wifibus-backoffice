package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Auditoria de entidades.
 * 
 * @author Luis Stubbia
 *
 */
@Entity
@Table(name = "AUDITS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Audit extends AbstractEntity {
	
	@Column(name = "ENTITY", nullable = false, length = 50)
	@NotNull
	private String entity;
	
	@Column(name = "ENTITY_ID", nullable = false)
	@NotNull
	private Long entityId;
	
	@Column(name = "OLD_VALUE", nullable = false, length = 500)
	@NotNull
	private String oldValue;
	
	@Column(name = "NEW_VALUE", nullable = false, length = 500)
	@NotNull
	private String newValue;
	
}

