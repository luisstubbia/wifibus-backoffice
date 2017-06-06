package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.ServiceTerm;
import com.vates.wifibus.backoffice.model.TermsForm;

/**
 * Service: branding service
 * 
 * @author Luis Stubbia
 *
 */
public interface ServiceTermService extends EntityService<ServiceTerm> {
	
    /**
     * Get terms into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<ServiceTerm>
     */
    Page<ServiceTerm> getTerms(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a terms.
     * @param term form
     */
	void addOrUpdateTerms(TermsForm terms);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}