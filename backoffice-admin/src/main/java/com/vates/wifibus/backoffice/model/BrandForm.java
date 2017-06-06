package com.vates.wifibus.backoffice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Branding data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class BrandForm {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private String cobrand;
	
	private String logoImage;
	
	private String backgroundImage;
	
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

	public String getCobrand() {
		return cobrand;
	}

	public void setCobrand(String cobrand) {
		this.cobrand = cobrand;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
}

