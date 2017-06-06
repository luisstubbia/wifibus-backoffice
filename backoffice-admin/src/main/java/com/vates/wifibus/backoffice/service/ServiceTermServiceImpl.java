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

import com.vates.wifibus.backoffice.model.ServiceTerm;
import com.vates.wifibus.backoffice.model.TermsForm;
import com.vates.wifibus.backoffice.repository.ServiceTermRepository;

/**
 * Service: Terms and conditions service, used to handle Terms info.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class ServiceTermServiceImpl implements ServiceTermService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTermServiceImpl.class);
    
	@Autowired
	private ServiceTermRepository termsRepository;

	@Override
	public Optional<ServiceTerm> getById(long id) {
		return Optional.ofNullable(termsRepository.findOne(id));
	}

	@Override
	public Optional<ServiceTerm> getByName(String termName) {
    	return termsRepository.findOneByName(termName);
	}
	
	@Override
	public Collection<ServiceTerm> getAll() {
		return termsRepository.findAll(new Sort("name"));
	}

	@Override
	public ServiceTerm save(ServiceTerm term) {
		return termsRepository.save(term);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting ServiceTerm by id={}", id);
		termsRepository.deleteById(id);
	}

	@Override
	public Page<ServiceTerm> getTerms(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return termsRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	public void addOrUpdateTerms(TermsForm termsForm) {
        if(termsForm.getId() != null){
        	termsRepository.setTermsInfoByNameAndDescriptionAndText(termsForm.getName(), 
        			termsForm.getDescripcion(), termsForm.getText(), termsForm.getId());
        } else {
        	ServiceTerm terms = new ServiceTerm();
            BeanUtils.copyProperties(termsForm, terms);
            termsRepository.save(terms);
        }	
	}

	@Override
	public Long countByName(String name) {
		return termsRepository.countByName(name);
	}
	
}