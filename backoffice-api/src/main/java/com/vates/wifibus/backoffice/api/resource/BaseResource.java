package com.vates.wifibus.backoffice.api.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Recurso base.
 * 
 * @author luis.stubbia
 * @param <T>
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseResource<T> {

	public static final String SELF_LINK = "self";
	public static final String CAMPAIGN_LINK = "campaigns";
	public static final String PROFILE_LINK = "profiles";
	public static final String SESSION_LINK = "sessions";
	
	private Map<String, EntityLink> links;

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

	/**
	 * Get links
	 * 
	 * @return
	 */
	public Map<String, EntityLink> getLinks() {
		return links;
	}

	/**
	 * Get link
	 * 
	 * @param rel
	 * @return link
	 */
	public EntityLink getLink(String rel) {
		return links.get(rel);
	}

	/**
	 * Adds new link
	 * 
	 * @param link
	 */
	public void addLink(String rel, EntityLink link) {
		if (CollectionUtils.isEmpty(links)) {
			links = new HashMap<String, EntityLink>();
		}
		links.put(rel, link);
	}

	/**
	 * Remove link
	 * 
	 * @param rel
	 * @return
	 */
	public EntityLink removeLink(String rel) {
		return this.links.remove(rel);
	}

	/**
	 * Get self link
	 */
	public void setSelf(String type) {
		if(StringUtils.isEmpty(type)){
			type = "GET";
		}
		String selfUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
		addLink(SELF_LINK, new EntityLink(type, selfUri));
	}

	/**
	 * Adding custom links
	 * 
	 * @param resource
	 */
	abstract void populateLinks(T resource);
}
