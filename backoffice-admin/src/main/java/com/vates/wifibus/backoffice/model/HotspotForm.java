package com.vates.wifibus.backoffice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Hotspot data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class HotspotForm {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private Router router;

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

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}
	
}

