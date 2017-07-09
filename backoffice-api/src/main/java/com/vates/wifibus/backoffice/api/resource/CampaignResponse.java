package com.vates.wifibus.backoffice.api.resource;

import java.util.Set;

import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.Campaign;

/**
 * 
 * @author luis.stubbia
 *
 */
public class CampaignResponse extends BaseResource{

	private Long profileId;
	private Long campaignId;
	private String name;
	private String landingUrl;
	private Set<Advertisement> advertisements;
	
	public CampaignResponse(Campaign campaign, Long profileId){
		this.profileId = profileId;
		this.campaignId = campaign.getId();
		this.name = campaign.getName();
		this.landingUrl = campaign.getLandingUrl();
		this.advertisements = campaign.getAdvertisements();
	}
	
	public CampaignResponse() {

	}

	public Long getProfileId() {
		return profileId;
	}
	
	public Long getCampaignId() {
		return campaignId;
	}
	
	public String getName() {
		return name;
	}

	public String getLandingUrl() {
		return landingUrl;
	}

	public Set<Advertisement> getAdvertisements() {
		return advertisements;
	}
}
