package com.vates.wifibus.backoffice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Question data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class QuestionForm {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private String label;
	
	private boolean open;
	
	private QuestionType type;
	
	private Collection<AnswerForm> answerForms = new ArrayList<AnswerForm>();
	
	public QuestionForm(){
		open = true;
		type = QuestionType.TEXT;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public Collection<AnswerForm> getAnswerForms() {
		return answerForms;
	}

	public void setAnswerForms(Collection<AnswerForm> answerForms) {
		this.answerForms = answerForms;
	}

	public void buildAnswers(Set<Answer> answers) {
		if(!CollectionUtils.isEmpty(answers)){
			int index = 1;
			for(Answer anw : answers){
				AnswerForm anwForm = new AnswerForm();
				BeanUtils.copyProperties(anw, anwForm);
				anwForm.setIndex(index++);
				this.answerForms.add(anwForm);
			}
		}
	}

	public void addAnswerForm() {
		AnswerForm anwForm = new AnswerForm();
		anwForm.setIndex(answerForms.size() + 1);
		answerForms.add(anwForm);
	}
}

