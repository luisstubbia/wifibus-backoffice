package com.vates.wifibus.backoffice.api.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.api.util.QuestionBuilder;
import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.ButtonType;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;
import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.model.SegmentItem;
import com.vates.wifibus.backoffice.repository.CampaignRepository;
import com.vates.wifibus.backoffice.repository.SegmentRepository;

/**
 * 
 * @author luis.stubbia
 *
 */
@Service
public class CampaignServiceImpl implements CampaignService {
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private SegmentRepository segmentRepository;
		
	@Override
	public CampaignResponse filterAdvertisements(Long campaignId, Profile profile) throws Exception {
		CampaignResponse campaignReq = new CampaignResponse();
		Campaign campaign = applyFilters(campaignId, profile);
		if(campaign != null){
			campaignReq = new CampaignResponse(campaign, profile.getId());
		} else {
			campaignReq.addError(new BussinesError(ErrorCode.ADVERTISEMENT_NOT_FOUND));
		}
		return campaignReq;
	}

	@Override
	public CampaignResponse getCampaign(Long campaignId) {
		CampaignResponse campaignReq = new CampaignResponse();
		if (campaignId != null) {
			Campaign camp = campaignRepository.findOne(campaignId);
			if(camp != null){
				campaignReq = new CampaignResponse(camp, null);
			} else {
				campaignReq.addError(new BussinesError(ErrorCode.CAMPAIGN_NOT_FOUND));
			}
		} else {
			campaignReq.addError(new BussinesError(ErrorCode.BAD_INPUT));
		}
		return campaignReq;
	}
	
	/**
	 * Filter advertisements according to the profile.
	 * @param campaignId
	 * @param profile
	 * @return list of advs
	 */
	private Campaign applyFilters(Long campaignId, Profile profile) throws Exception {
		List<Segment> segments = segmentRepository.getSegmentByCampaign(campaignId);
		List<Long> segmentIds;
		if(profile.getLoginSource().equals(ButtonType.FACEBOOK)) {
			segmentIds = filterByFacebook(segments, profile);
		} else {
			segmentIds = filterByQuestions(segments, profile);
		}
		
		if(CollectionUtils.isEmpty(segmentIds)){
			return null;
		} else {
			Campaign campaign = campaignRepository.findOne(campaignId);
			Iterator<Advertisement> advIt = campaign.getAdvertisements().iterator();
			while(advIt.hasNext()){
				Advertisement adv = advIt.next();
				if(adv.getSegment() != null && !segmentIds.contains(adv.getSegment().getId())){
					advIt.remove();
				}
			}
			return campaign;
		}
	}

	/**
	 * Get valid segments.
	 * 
	 * @param segments
	 * @param profile
	 * @param campaign
	 * @return segment list
	 * @throws Exception
	 */
	private List<Long> filterByQuestions(List<Segment> segments, Profile profile) throws Exception {
		List<Long> segmentIds = new ArrayList<Long>();
		Iterator<Segment> segIt = segments.iterator();
		while(segIt.hasNext()){
			Segment seg = segIt.next();
			boolean validSegment = true;
			for(SegmentItem item : seg.getItems()){
				boolean validQuestion = false;
				for(ProfileValue answer : profile.getValues()){
					if(item.getQuestion().getName().equals(answer.getKey())){
						validQuestion = true;
						QuestionBuilder builder = QuestionBuilder.builder(item.getQuestion().getType());
						if(!builder.validAnswer(item, answer.getValue())){
							validQuestion = false;
							break;
						}
					}
				}
				if(!validQuestion){
					validSegment = false;
					break;
				}
			}
			if(!validSegment){
				segIt.remove();
			} else {
				segmentIds.add(seg.getId());
			}
		}
		return segmentIds;
	}
	
	private List<Long> filterByFacebook(List<Segment> segments, Profile profile) {
		// TODO Auto-generated method stub
		return null;
	}
}
