package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
		return segmentRepository.findByEnabledTrueOrderByNameDesc();
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
		mergeItems(segment, segmentForm);
        BeanUtils.copyProperties(segmentForm, segment, "id", "items");
        segmentRepository.save(segment);
	}

	private void mergeItems(final Segment segment, SegmentForm segmentForm) {
		segmentForm.getItems().forEach(item->item.setSegment(segment));
		if(!CollectionUtils.isEmpty(segment.getItems())){
			segment.getItems().forEach(item->item.setSegment(null));
			// Adding new items
			segmentForm.getItems().forEach(item->{
				if(item.getId() == null)
					segment.getItems().add(item);
			});
			// Removing and updating items that already exist.
			Iterator<SegmentItem> it = segment.getItems().iterator();
			while(it.hasNext()){
				boolean exist = false;
				SegmentItem itm = (SegmentItem) it.next();
				for(SegmentItem formItm : segmentForm.getItems()){
					if(itm.getId() != null && formItm.getId() != null && itm.getId().intValue() == formItm.getId().intValue()){
						BeanUtils.copyProperties(formItm, itm, "id");
						exist = true;
						break;
					}
				}
				if(!exist && itm.getId() != null){
					it.remove();
				}
			}
		} else {
			segment.setItems(new LinkedHashSet<SegmentItem>(segmentForm.getItems()));
		}
	}

	@Override
	public Long countByName(String name) {
		return segmentRepository.countByName(name);
	}
}