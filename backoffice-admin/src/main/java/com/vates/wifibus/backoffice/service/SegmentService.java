package com.vates.wifibus.backoffice.service;

import org.springframework.data.domain.Page;

import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.model.SegmentForm;

/**
 * Service: Segment service
 * 
 * @author Luis Stubbia
 *
 */
public interface SegmentService extends EntityService<Segment> {
	
    /**
     * Get Segments into a Page structure
     * @param pageNumber
     * @param pageSize
     * @param searchText
     * @return Page<Segment>
     */
    Page<Segment> getSegments(Integer pageNumber, Integer pageSize, String searchText);

    /**
     * Add or Update a Segment.
     * @param Segment form
     */
	void addOrUpdateSegment(SegmentForm segment);
	
	/**
	 * Returns number of duplicated names.
	 * @param name
	 * @return long
	 */
	Long countByName(String name);
	
}