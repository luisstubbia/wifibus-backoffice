package com.vates.wifibus.backoffice.api.resource;

import java.util.Set;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.Campaign;

public class CampaignResponse extends BaseResource<Campaign>{

	private Long profileId;
	private String name;
	private String landingUrl;
	private Set<Advertisement> advertisements;
	
	public CampaignResponse(Campaign campaign, Long profileId){
		this.profileId = profileId;
		this.name = campaign.getName();
		this.landingUrl = campaign.getLandingUrl();
		this.advertisements = campaign.getAdvertisements();
	}
	
	public CampaignResponse() {

	}

	public Long getProfileId() {
		return profileId;
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
	
	@Override
	public void populateLinks(Campaign resource) {
		setSelf("POST");
		
		String campaignUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + CAMPAIGN_LINK + "/{id}")
				.buildAndExpand(resource.getId()).toUriString();
		addLink(CAMPAIGN_LINK, new EntityLink("GET", campaignUri));
	}
}
