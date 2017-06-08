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
import org.springframework.transaction.annotation.Transactional;

import com.vates.wifibus.backoffice.model.Audit;
import com.vates.wifibus.backoffice.model.Router;
import com.vates.wifibus.backoffice.model.RouterForm;
import com.vates.wifibus.backoffice.repository.AuditRepository;
import com.vates.wifibus.backoffice.repository.RouterRepository;

/**
 * Service: Router service, used to handle router info.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class RouterServiceImpl implements RouterService {

    private static final Logger logger = LoggerFactory.getLogger(RouterServiceImpl.class);
    
	@Autowired
	private RouterRepository routerRepository;

	@Autowired
	private AuditRepository auditRepository;
	
	@Override
	public Optional<Router> getById(long id) {
		return Optional.ofNullable(routerRepository.findOne(id));
	}

	@Override
	public Optional<Router> getByName(String routerName) {
    	return routerRepository.findOneByName(routerName);
	}
	
	@Override
	public Collection<Router> getAll() {
		return routerRepository.findAll(new Sort("name"));
	}

	@Override
	public Router save(Router router) {
		return routerRepository.save(router);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting router by id={}", id);
		routerRepository.deleteById(id);
	}

	@Override
	public Page<Router> getRouters(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return routerRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	@Transactional
	public void addOrUpdateRouter(RouterForm routerForm) {
        if(routerForm.getId() != null){
        	Router router = routerRepository.findOne(routerForm.getId());
        	routerRepository.setRouterInfoByNameMacIpLocationDescLatitudeAndLonguitude(routerForm.getName(),
        			routerForm.getDescripcion(), routerForm.getMacAddress(), routerForm.getIpv4address(), 
        			routerForm.getLocation(), routerForm.getLatitude(), routerForm.getLongitude(), 
        			routerForm.getGroup().getId(), routerForm.getId());
        	addAuditLog(router, routerForm);
        } else {
        	Router router = new Router();
            BeanUtils.copyProperties(routerForm, router);
            router.setIpV4Address(routerForm.getIpv4address());
            routerRepository.save(router);
        }
	}

	/**
	 * Adds audit entity for logging.
	 * @param router
	 * @param routerForm
	 */
	private void addAuditLog(Router router, RouterForm routerForm) {
		Audit audit = new Audit();
    	audit.setEntity(RouterForm.class.getName());
    	audit.setEntityId(routerForm.getId());
    	audit.setOldValue(router.toString());
    	audit.setNewValue(routerForm.toString());
    	auditRepository.save(audit);
	}

	@Override
	public Long countByMacAddress(String macAddresss) {
		return routerRepository.countByMacAddress(macAddresss);
	}

	@Override
	public Long countByIpAddress(String ipAddress) {
		return routerRepository.countByIpV4Address(ipAddress);
	}
	
	@Override
	public Long countByName(String name) {
		return routerRepository.countByName(name);
	}
	
}