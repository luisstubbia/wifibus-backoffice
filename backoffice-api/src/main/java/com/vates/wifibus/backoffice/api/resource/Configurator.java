package com.vates.wifibus.backoffice.api.resource;

import java.util.Set;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vates.wifibus.backoffice.api.config.ApplicationConfiguration;
import com.vates.wifibus.backoffice.model.Brand;
import com.vates.wifibus.backoffice.model.ButtonType;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.Question;
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

	private Config config;
	private Brand branding;
	private ServiceTerm termsAndConditions;
	private Campaign campaign;
	private Set<ButtonType> buttons;
	private Set<Question> questions;

	public Configurator() {

	}

	public Configurator(RouterGroup group) {
		this.branding = group.getBrand();
		this.termsAndConditions = group.getTermAndCondition();
		this.campaign = group.getCampaign();
		this.questions = group.getQuestions();
		this.buttons = group.getButtons();
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

	public Set<Question> getQuestions() {
		return questions;
	}
	
	public Set<ButtonType> getButtons() {
		return buttons;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(ApplicationConfiguration app) {
		config = new Config();
		config.setApiKeys(app.getApiKeys());
	}
	
	@Override
	public void populateLinks(RouterGroup resource) {
		setSelf();

		String campaignUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + CAMPAIGN_LINK + "/{id}")
				.buildAndExpand(resource.getCampaign().getId()).toUriString();
		addLink(CAMPAIGN_LINK, new EntityLink("GET", campaignUri));

		String profileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/" + CAMPAIGN_LINK + "/{id}").buildAndExpand(resource.getCampaign().getId())
				.toUriString();
		addLink(PROFILE_LINK, new EntityLink("POST", profileUri));

	}
}
