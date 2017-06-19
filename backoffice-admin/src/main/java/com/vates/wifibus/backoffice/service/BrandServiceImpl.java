package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.Brand;
import com.vates.wifibus.backoffice.model.BrandForm;
import com.vates.wifibus.backoffice.repository.BrandingRepository;

/**
 * Service: Brand service, used to handle Brand info.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
    
	@Autowired
	private BrandingRepository brandRepository;

	@Override
	public Optional<Brand> getById(long id) {
		return Optional.ofNullable(brandRepository.findOne(id));
	}

	@Override
	public Optional<Brand> getByName(String brandName) {
    	return brandRepository.findOneByName(brandName);
	}
	
	@Override
	public Collection<Brand> getAll() {
		return brandRepository.findByEnabledTrueOrderByNameDesc();
	}

	@Override
	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting Brand by id={}", id);
		brandRepository.deleteById(id);
	}

	@Override
	public Page<Brand> getBrands(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return brandRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	public void addOrUpdateBrand(BrandForm brandForm) {
        if(brandForm.getId() != null){
        	brandRepository.setBrandInfoByNameAndDescriptionAndCobrandAndLogoImageAndBackgroundImge(brandForm.getName(), 
        			brandForm.getDescripcion(), brandForm.getCobrand(), brandForm.getLogoImage(),
        			brandForm.getBackgroundImage(), brandForm.getId());
        } else {
        	Brand brand = new Brand();
            BeanUtils.copyProperties(brandForm, brand);
            brandRepository.save(brand);
        }	
	}

	@Override
	public Long countByName(String name) {
		return brandRepository.countByName(name);
	}
	
}