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
 * Entity: Branding model.
 * 
 * @author luis.stubbia
 *
 */
@Entity
@Table(name = "BRANDS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Brand extends BaseEntity {
	
	@Column(name = "COBRAND", nullable = false, length = 500)
	@NotNull
	private String cobrand;
	
	@Column(name = "LOGO_IMAGE_URL", nullable = false, length = 250)
	@NotNull
	private String logoImage;
	
	@Column(name = "BACKGROUND_IMAGE_URL", nullable = false, length = 250)
	@NotNull
	private String backgroundImage;
	
}
