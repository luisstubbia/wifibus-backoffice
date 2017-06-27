package com.vates.wifibus.backoffice.api.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.Campaing;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.api.resource.Filter;
import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.model.SegmentItem;
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
	public Campaing getAdvertisements(Long campaignId, Filter filter) {
		Profile profile = loadProfile(filter);
		Campaing campaign = new Campaing(filterAdvertisements(campaignId, profile));
		return campaign;
	}

	@Override
	public Campaing getCampaign(Long campaignId) {
		Campaing campaign = new Campaing();
		if (campaignId != null) {
			Campaign camp = campaignRepository.findOne(campaignId);
			if(camp != null){
				campaign = new Campaing(camp.getAdvertisements());
				campaign.populateLinks(null);
			} else {
				campaign.addError(new BussinesError(ErrorCode.CAMPAIGN_NOT_FOUND));
			}
		} else {
			campaign.addError(new BussinesError(ErrorCode.BAD_INPUT));
		}
		return campaign;
	}
	
	/**
	 * Filter advertisements according to the profile.
	 * @param campaignId
	 * @param profile
	 * @return list of advs
	 */
	private Set<Advertisement> filterAdvertisements(Long campaignId, Profile profile) {
		List<Segment> segments = segmentRepository.getSegmentByCampaing(campaignId);
		for(Segment segment : segments){
			for(SegmentItem item : segment.getItems()){
				item.getQuestion().getName();
			}
		}
		
		return null;
	}

	/**
	 * This method tries to match the filters into profile object.
	 * 
	 * @param filter
	 * @return profile
	 */
	private Profile loadProfile(Filter filter) {
		// Profile Engine or Profile Factory?
		// Input: filters - engine/factory -> filter.externalLogin |facebook|google|mail|
		// TODO Auto-generated method stub
		profileRepository.save(new Profile());
		return null;
	}
}
