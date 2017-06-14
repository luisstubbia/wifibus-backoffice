package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Cada segmento identifica el publico destinado en funcion de ciertos
 * parametros definidos por preguntas y respuestas.
 * 
 * @author Luis Stubbia
 *
 */
@Entity
@Inheritance
@Table(name = "SEGMENT_ITEMS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class SegmentItem extends AbstractEntity {

	@ManyToOne
    @JoinColumn(name = "QUESTION_ID")
	private Question question;
	
	@Column(name = "OPERATOR", nullable = false)
	@Enumerated(EnumType.STRING)
	private OperatorType operator;
	
	@Column(name = "VALUE", nullable = false)
	private String value;
	
	@ManyToOne
    @JoinColumn(name = "SEGMENT_ID")
	@NotNull
	private Segment segment;
	
	@Transient
	@JsonIgnore
	private Integer index;
}
