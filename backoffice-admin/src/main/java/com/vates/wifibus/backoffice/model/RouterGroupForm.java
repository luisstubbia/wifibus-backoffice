package com.vates.wifibus.backoffice.model;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * DTO: Group form used as data tranfer obj.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class RouterGroupForm {

	private Long id;
    
	private String name;
  
	private String descripcion;
	
	private Brand brand;
	
	private ServiceTerm termAndCondition;
	
	private Campaign campaign;
	
	private Set<Question> questions = new LinkedHashSet<Question>();

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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public ServiceTerm getTermAndCondition() {
		return termAndCondition;
	}

	public void setTermAndCondition(ServiceTerm termAndCondition) {
		this.termAndCondition = termAndCondition;
	}
    
	public void addQuestion(Question question){
		questions.add(question);
	}
}
