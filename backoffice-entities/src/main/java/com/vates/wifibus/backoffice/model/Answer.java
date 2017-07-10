package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
 * Modela las posibles respuestas a los tipos de preguntas que son cerradas.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Table(name = "ANSWERS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Answer extends LabeledEntity {

	@Column(name = "VALUE", nullable = false)
	@NotNull
	private Integer value;
	
	@ManyToOne
    @JoinColumn(name = "QUESTION_ID")
	@NotNull
	@JsonIgnore
	private Question question;
	
	@Transient
	@JsonIgnore
	private Integer index;
	
	@Override
	public String toString(){
		return getId() + " - value: " + this.value;
	}
}
