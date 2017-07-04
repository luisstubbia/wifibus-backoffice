package com.vates.wifibus.backoffice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	private boolean open = true;
	
	@Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType type;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<Answer> answers;
	
	@ElementCollection
	@CollectionTable(name = "QUESTIONS_PROPERTIES", 
	        joinColumns = @JoinColumn(name = "QUESTION_ID")
	)
	@Column(name = "PROPERTIES")
    private Set<String> properties;
}
