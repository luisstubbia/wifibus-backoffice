package com.vates.wifibus.backoffice.api.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Recurso base.
 * 
 * @author luis.stubbia
 * @param <T>
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseResource {

	public static final String SELF_LINK = "self";
	public static final String CAMPAIGN_LINK = "campaigns";
	public static final String PROFILE_LINK = "profiles";
	public static final String SESSION_LINK = "sessions";

	private List<BussinesError> errors;

	/**
	 * Get errors
	 * 
	 * @return
	 */
	public List<BussinesError> getErrors() {
		return errors;
	}

	/**
	 * Set errors
	 * 
	 * @param errors
	 */
	public void setErrors(List<BussinesError> errors) {
		this.errors = errors;
	}

	/**
	 * Adds new error
	 * 
	 * @param error
	 */
	public void addError(BussinesError error) {
		if (CollectionUtils.isEmpty(errors)) {
			errors = new ArrayList<BussinesError>();
		}
		errors.add(error);
	}

	/**
	 * Validates if the resource has errors
	 * 
	 * @return
	 */
	public boolean hasErrors() {
		return !CollectionUtils.isEmpty(errors);
	}
}
