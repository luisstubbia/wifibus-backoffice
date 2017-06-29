package com.vates.wifibus.backoffice.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.resource.FilterRequest;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.repository.CampaignRepository;
import com.vates.wifibus.backoffice.repository.ProfileRepository;
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
	private ProfileRepository profileRepository;
	
	@Override
	public CampaignResponse filterAdvertisements(Long campaignId, Long profileId, FilterRequest filter) {
		CampaignResponse campaignReq = new CampaignResponse();
		validateRequest(filter, campaignReq);
		if(!campaignReq.hasErrors()){
			Campaign campaign = campaignRepository.findOne(campaignId);
			Profile profile = profileRepository.findOne(profileId);
			if(campaign != null && profile != null){
				applyFilters(campaign, profile, filter);
				campaignReq = new CampaignResponse(campaign);
				campaignReq.populateLinks(campaign);
			} else {
				if(campaign == null){
					campaignReq.addError(new BussinesError(ErrorCode.CAMPAIGN_NOT_FOUND));
				} else {
					campaignReq.addError(new BussinesError(ErrorCode.PROFILE_NOT_FOUND));
				}
			}
		}
		return campaignReq;
	}

	@Override
	public CampaignResponse getCampaign(Long campaignId) {
		CampaignResponse campaignReq = new CampaignResponse();
		if (campaignId != null) {
			Campaign camp = campaignRepository.findOne(campaignId);
			if(camp != null){
				campaignReq = new CampaignResponse(camp);
				campaignReq.populateLinks(camp);
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
	private void applyFilters(Campaign campaign, Profile profile, FilterRequest filter) {
		List<Segment> segments = segmentRepository.getSegmentByCampaing(campaign.getId());
		filterByQuestions(segments, filter);
		filterByProfile(segments, profile);
	}

	private void filterByProfile(List<Segment> segments, Profile profile) {
		// TODO Auto-generated method stub
		
	}

	private void filterByQuestions(List<Segment> segments, FilterRequest filter) {
		// TODO Auto-generated method stub
		
	}
	
	private void validateRequest(FilterRequest filter, CampaignResponse campaignReq) {
		// TODO Auto-generated method stub
		
	}
}
