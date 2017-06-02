package com.vates.wifibus.backoffice.model;

import lombok.Data;

/**
 * DTO: Hotspot data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
public class HotspotForm {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private Router router;
	
}

