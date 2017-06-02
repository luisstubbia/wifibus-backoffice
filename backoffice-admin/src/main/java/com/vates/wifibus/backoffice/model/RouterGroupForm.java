package com.vates.wifibus.backoffice.model;

import lombok.Data;

/**
 * 
 * DTO: Group form used as data tranfer obj.
 * 
 * @author Luis Stubbia
 *
 */
@Data
public class RouterGroupForm {

	private Long id;
    
	private String name;
  
	private String descripcion;
    
}
