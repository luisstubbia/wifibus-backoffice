package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.Brand;
import com.vates.wifibus.backoffice.model.BrandForm;

/**
 * Service: branding service
 * 
 * @author Luis Stubbia
 *
 */
public interface BrandService extends EntityService<Brand> {
	
    /**
     * Get Brands into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<Brand>
     */
    Page<Brand> getBrands(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a brand.
     * @param brand form
     */
	void addOrUpdateBrand(BrandForm brand);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}