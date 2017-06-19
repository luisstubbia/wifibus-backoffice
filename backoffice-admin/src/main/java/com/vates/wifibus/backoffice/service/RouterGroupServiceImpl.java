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

import com.vates.wifibus.backoffice.model.RouterGroup;
import com.vates.wifibus.backoffice.model.RouterGroupForm;
import com.vates.wifibus.backoffice.repository.RouterGroupRepository;

/**
 * Service: Group service, used to handle group entities.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class RouterGroupServiceImpl implements RouterGroupService {

    private static final Logger logger = LoggerFactory.getLogger(RouterGroupServiceImpl.class);
    
	@Autowired
	private RouterGroupRepository groupRepository;

	@Override
	public Optional<RouterGroup> getById(long id) {
		return Optional.ofNullable(groupRepository.findOne(id));
	}

	@Override
	public Optional<RouterGroup> getByName(String groupName) {
    	return groupRepository.findOneByName(groupName);
	}
	
	@Override
	public Collection<RouterGroup> getAll() {
		return groupRepository.findByEnabledTrueOrderByNameDesc();
	}

	@Override
	public RouterGroup save(RouterGroup group) {
		return groupRepository.save(group);
	}

	@Override
	public void addOrUpdateRouterGroup(RouterGroupForm routerGroupForm) {
		RouterGroup routerGroup = new RouterGroup();
		if(routerGroupForm.getId() != null){
			routerGroup = groupRepository.getOne(routerGroupForm.getId());
		}
        BeanUtils.copyProperties(routerGroupForm, routerGroup, "id");
        routerGroup.setQuestions(routerGroupForm.getQuestions());
		groupRepository.save(routerGroup);
	}
	
	@Override
	public void deleteById(long id) {
		logger.debug("Deleting group by id={}", id);
		groupRepository.deleteById(id);
	}
	
	@Override
	public Page<RouterGroup> getRouterGroups(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return groupRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	public Long countByName(String name) {
		return groupRepository.countByName(name);
	}

}