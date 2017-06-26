package com.vates.wifibus.backoffice.api.resource;

import java.util.Map;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vates.wifibus.backoffice.model.Brand;
import com.vates.wifibus.backoffice.model.ButtonType;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.RouterGroup;
import com.vates.wifibus.backoffice.model.ServiceTerm;

/**
 * Recurso de configuraciones
 * 
 * @author luis.stubbia
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Configurator extends BaseResource<RouterGroup> {

	public static final String CAMPAIGN_LINK = "campaigns";
	public static final String PROFILE_LINK = "profiles";

	private Brand branding;
	private ServiceTerm termsAndConditions;
	private Campaign campaign;
	private Map<String, ButtonType> buttons;

	public Configurator() {

	}

	public Configurator(RouterGroup group) {
		this.branding = group.getBrand();
		this.termsAndConditions = group.getTermAndCondition();
		this.campaign = group.getCampaign();
	}

	public Brand getBranding() {
		return branding;
	}

	public ServiceTerm getTermsAndConditions() {
		return termsAndConditions;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public Map<String, ButtonType> getButtons() {
		return buttons;
	}

	@Override
	public void populateLinks(RouterGroup resource) {
		setSelf();

		String campaignUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + CAMPAIGN_LINK + "/{id}")
				.buildAndExpand(resource.getCampaign().getId()).toUriString();
		addLink(CAMPAIGN_LINK, new EntityLink("GET", campaignUri));

		String profileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/" + PROFILE_LINK + "/campaign/{id}").buildAndExpand(resource.getCampaign().getId())
				.toUriString();
		addLink(PROFILE_LINK, new EntityLink("POST", profileUri));

	}

	public void fillButtons(Map<String, ButtonType> buttons) {
		this.buttons = buttons;
	}
}
