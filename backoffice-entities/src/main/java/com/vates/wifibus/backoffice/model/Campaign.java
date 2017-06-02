package com.vates.wifibus.backoffice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Modela las campañas de marketing que aplican a los grupos de hotspots.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Table(name = "CAMPAIGNS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Campaign extends BaseEntity {

	@Column(name = "CAPTION", nullable = false, unique = false, length = 1024)
	@NotNull
	private String landingUrl;
    
	@OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<Advertisement> advertisements;
	
}
