package com.vates.wifibus.backoffice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Modela el tipo de preguntas que se hacen al usuario final.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Table(name = "QUESTIONS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Question extends LabeledEntity {

	@Column(name = "IS_OPEN", nullable = false)
	@NotNull
	private boolean open = true;
	
	@Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType type;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answers;
	
}
