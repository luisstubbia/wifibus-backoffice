package com.vates.wifibus.backoffice.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Modela los hotspots asociados a un router.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Table(name = "HOTSPOTS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Hotspot extends BaseEntity {
	
	@ManyToOne
    @JoinColumn(name = "ROUTER_ID")
	@NotNull
	@JsonIgnore
	private Router router;
	
}
