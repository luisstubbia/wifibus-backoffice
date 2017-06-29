package com.vates.wifibus.backoffice.api.resource;

import java.util.Map;

import com.vates.wifibus.backoffice.model.QuestionType;

/**
 * Solicitud para filtrar los anuncios de una campaña
 * 
 * @author luis.stubbia
 *
 */
public class FilterRequest {
	
	private Map<QuestionType, String> filters;

	public Map<QuestionType, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<QuestionType, String> filters) {
		this.filters = filters;
	}
}