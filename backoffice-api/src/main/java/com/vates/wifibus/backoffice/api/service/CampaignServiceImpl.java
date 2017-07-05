package com.vates.wifibus.backoffice.api.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vates.wifibus.backoffice.api.config.ApplicationConfiguration;
import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.api.util.ProfileMapper;
import com.vates.wifibus.backoffice.api.util.QuestionBuilder;
import com.vates.wifibus.backoffice.model.Advertisement;
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
	
	@Autowired
	private ApplicationConfiguration app;
	
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
		segmentIds = filterByProfile(segments, profile);
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
	private List<Long> filterByProfile(List<Segment> segments, Profile profile) throws Exception {
		ProfileMapper mapper = ProfileMapper.getMapper(profile);
		mapper.setApp(app);
		List<Long> segmentIds = new ArrayList<Long>();
		Iterator<Segment> segIt = segments.iterator();
		while(segIt.hasNext()){
			Segment seg = segIt.next();
			boolean validSegment = true;
			for(SegmentItem item : seg.getItems()){
				ProfileValue answer = mapper.getValue(item.getQuestion(), profile);
				if(answer != null){
					QuestionBuilder builder = QuestionBuilder.builder(item.getQuestion().getType());
					if(!builder.validAnswer(item, answer.getValue())){
						validSegment = false;
						break;
					}
				} else {
					validSegment = false;
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
}
