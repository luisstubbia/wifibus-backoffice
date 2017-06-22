package com.vates.wifibus.backoffice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Modela los tipos de anuncios asociados a las campañas de marketing.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Inheritance
@Table(name = "ADVERTISEMENTS")
@DiscriminatorColumn(name="TYPE")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Advertisement extends BaseEntity {
	
	@ManyToOne
    @JoinColumn(name = "CAMPAIGN_ID")
	@NotNull
	private Campaign campaign;
	
	@Column(name = "START_DATE", nullable = false)
	@NotNull
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "DURATION", nullable = false)
	@NotNull
	private Integer duration;

	@Column(name = "PRIORITY", nullable = false)
	@NotNull
	private Integer priority;
	
	@ManyToOne
    @JoinColumn(name = "SEGMENT_ID")
	private Segment segment;
	
}
