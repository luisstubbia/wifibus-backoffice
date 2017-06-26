package com.vates.wifibus.backoffice.api.resource;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Mapper para errores de negocio
 * 
 * @author luis.stubbia
 *
 */
public class BussinesError {

	private final HttpStatus status;
    private final String code;
    private final String description;
    private final String type;
    private final List<String> details;
    
    /**
     * Full constructor.
     * 
     * @param error
     * @param details
     */
    public BussinesError(ErrorCode error, List<String> details){
    	this.status = error.getStatus();
    	this.type = error.name();
        this.code = error.getCode();
        this.description = error.getDescription();
        this.details = details;
    }   

    public BussinesError(ErrorCode error){
    	this.status = error.getStatus();
    	this.type = error.name();
        this.code = error.getCode();
        this.description = error.getDescription();
        this.details = null; 
    }
    
    public BussinesError(String description, List<String> details){
    	this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    	this.type = ErrorCode.SERVICE_ERROR.name();
        this.code = ErrorCode.SERVICE_ERROR.getCode();
        this.description = description;
        this.details = details;
    }
    
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
    
    public String getType(){
        return type;
    }

    public List<String> getDetails() {
        return details;
    }

	public HttpStatus getStatus() {
		return status;
	}
}
