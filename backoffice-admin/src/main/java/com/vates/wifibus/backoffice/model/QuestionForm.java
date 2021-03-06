package com.vates.wifibus.backoffice.model;

import java.util.LinkedHashSet;
import java.util.Set;

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
	
	private Set<Answer> answers = new LinkedHashSet<Answer>();

	private Set<String> properties = new LinkedHashSet<String>();
	
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

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public void buildAnswers(Set<Answer> answers) {
		if(!CollectionUtils.isEmpty(answers)){
			int index = 1;
			for(Answer anw : answers){
				anw.setIndex(index++);
			}
		}
	}

	public void addAnswer() {
		Answer anw = new Answer();
		anw.setIndex(answers.size() + 1);
		answers.add(anw);
	}

	public Set<String> getProperties() {
		return properties;
	}

	public void setProperties(Set<String> properties) {
		this.properties = properties;
	}
	
	public void addProperty(String property){
		properties.add(property);
	}
}

