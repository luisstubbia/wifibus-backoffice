package com.vates.wifibus.backoffice.api.util;

import java.util.List;

import com.vates.wifibus.backoffice.api.resource.BussinesError;

/**
 * Exception Wrapper. Permite centralizar cualquier error de negocio en esta exception.
 * 
 * @author luis.stubbia
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 6963357305129439717L;
	
	private List<BussinesError> errors;

	public ServiceException(List<BussinesError> list){
		this.errors = list;
	}

	public List<BussinesError> getErrors() {
		return errors;
	}

	public void setErrors(List<BussinesError> errors) {
		this.errors = errors;
	}
	
	

}
