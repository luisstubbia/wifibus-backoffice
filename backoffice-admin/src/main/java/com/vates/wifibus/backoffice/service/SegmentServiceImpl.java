package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.model.SegmentForm;
import com.vates.wifibus.backoffice.model.SegmentItem;
import com.vates.wifibus.backoffice.repository.SegmentRepository;

/**
 * Service: Campaign service, used to handle Campaign info.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class SegmentServiceImpl implements SegmentService {

    private static final Logger logger = LoggerFactory.getLogger(SegmentServiceImpl.class);
    
	@Autowired
	private SegmentRepository segmentRepository;

	@Override
	public Optional<Segment> getById(long id) {
		return Optional.ofNullable(segmentRepository.findOne(id));
	}

	@Override
	public Optional<Segment> getByName(String segmentName) {
    	return segmentRepository.findOneByName(segmentName);
	}
	
	@Override
	public Collection<Segment> getAll() {
		return segmentRepository.findAll(new Sort("name"));
	}

	@Override
	public Segment save(Segment segment) {
		return segmentRepository.save(segment);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting Campaign by id={}", id);
		segmentRepository.deleteById(id);
	}

	@Override
	public Page<Segment> getSegments(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return segmentRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	public void addOrUpdateSegment(SegmentForm segmentForm) {
		Segment segment = new Segment();
		if(segmentForm.getId() != null){
			segment = segmentRepository.getOne(segmentForm.getId());
		}
        BeanUtils.copyProperties(segmentForm, segment, "id", "items");
        for(SegmentItem itm : segmentForm.getItems()){
        	itm.setSegment(segment);
        }
        Set<SegmentItem> items = new LinkedHashSet<SegmentItem>(segmentForm.getItems());
        segment.setItems(items);
        segmentRepository.save(segment);
	}

	@Override
	public Long countByName(String name) {
		return segmentRepository.countByName(name);
	}
}