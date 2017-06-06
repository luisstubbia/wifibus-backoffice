package com.vates.wifibus.backoffice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Terms and conditions data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class TermsForm {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private String text;
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

