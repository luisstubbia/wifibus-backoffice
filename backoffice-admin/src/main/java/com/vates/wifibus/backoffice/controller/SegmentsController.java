package com.vates.wifibus.backoffice.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vates.wifibus.backoffice.model.SegmentItem;
import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.model.SegmentForm;
import com.vates.wifibus.backoffice.service.QuestionService;
import com.vates.wifibus.backoffice.service.SegmentService;
import com.vates.wifibus.backoffice.validator.SegmentFormValidator;
/**
 * 
 * @author Gaston Napoli
 *
 */
@Controller
@SessionAttributes(types = SegmentForm.class)
public class SegmentsController {

	@Autowired
	private SegmentService segmentService;
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private SegmentFormValidator segmentValidator;
	
    @GetMapping("/segments")
    public String getSegmentsPage(Model model) {
    	Page<Segment> segments = segmentService.getSegments(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(segments));
    	model.addAttribute("selections", segments.getContent());
        return "segments";
    }
    
    @RequestMapping(value = "/segments", method = RequestMethod.POST)
    public String findSegmentsPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = true;
    	}
    	Page<Segment> segments = segmentService.getSegments(paginator.getSelectedPage(), 
    			paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if(segments != null && segments.getContent().size() == 1 && isQuery){
    		return "redirect:/segments/"+ segments.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(segments);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", segments.getContent());
        	return "segments";
    	}
    }
    
    @RequestMapping("/segments/new")
	public String getSegmentNewPage(Model model) {
    	SegmentForm segmentForm = new SegmentForm();
    	segmentForm.addSegmentItem();
		model.addAttribute("segmentForm", segmentForm);
		model.addAttribute("questions", questionService.getAll());
    	return "createOrUpdateSegmentForm";
    }
    
    @RequestMapping(value = "/segments/{segmentId}/edit", method = RequestMethod.GET)
    public String getSegmentInfoPage(@PathVariable("segmentId") Long segmentId, Model model) {
    	SegmentForm segmentForm = new SegmentForm();
    	if(null != segmentId){
    		Optional<Segment> segment = segmentService.getById(segmentId);
    		if(segment.isPresent()){
    			BeanUtils.copyProperties(segment.get(), segmentForm);
    			segmentForm.buildItem(segment.get().getItems());
    		}
    	}
    	model.addAttribute("questions", questionService.getAll());
    	model.addAttribute(segmentForm);
    	return "createOrUpdateSegmentForm";
    }
    
    @RequestMapping(value = {"/segments/{segmentId}/edit", "/segments/new"}, method = RequestMethod.POST)
    public String createSegmentInfoPage(SegmentForm segment, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, @ModelAttribute("removeItm") String itmId,  
    		SessionStatus status) {
    	model.addAttribute("questions", questionService.getAll());
        if(!StringUtils.isEmpty(itmId)){
        	removeItm(segment, Integer.parseInt(itmId));
        	return "createOrUpdateSegmentForm";
        }
        segmentValidator.validate(segment, result);
        if (result.hasErrors()) {
            return "createOrUpdateSegmentForm";
        } else {
        	segmentService.addOrUpdateSegment(segment);
        	status.setComplete();
            return "redirect:/segments";
        }
    }

	@RequestMapping(value = {"/segments/{segmentId}/edit", "/segments/new"}, method = RequestMethod.POST, params="action=addItm")
    public String addAnswerView(SegmentForm segment, BindingResult result, Model model, SessionStatus status) {
		segment.addSegmentItem();
		model.addAttribute("questions", questionService.getAll());
    	return "createOrUpdateSegmentForm";
    }
    
	@RequestMapping(value = "/segments/{id}/delete", method = RequestMethod.GET)
    public String deleteBrandPage(@PathVariable Long id) {
		segmentService.deleteById(id);
    	return "redirect:/segments";
    }
    
    /**
     * Remove segment item element from segment form.
     * @param segment
     * @param itmId
     */
    private void removeItm(SegmentForm segment, int itmId) {
		for(SegmentItem itm : segment.getItems()){
			if(itm.getIndex() == itmId){
				itm.setSegment(null);
				segment.getItems().remove(itm);
				break;
			}
		}
    }
}