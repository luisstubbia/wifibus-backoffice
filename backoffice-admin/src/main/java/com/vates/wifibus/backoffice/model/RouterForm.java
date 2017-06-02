package com.vates.wifibus.backoffice.model;

import java.util.Set;

import lombok.Data;

/**
 * DTO: Router data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
public class RouterForm {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private String macAddress;
	
	private String ipV4Address;
	
	private String location;
	
	private Double latitude;
	
	private Double longitude;
	
	private RouterGroup group;
	
    private Set<Hotspot> hotspots;
        
}
