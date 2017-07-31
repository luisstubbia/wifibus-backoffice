package com.vates.wifibus.backoffice.api.resource;

import java.util.Set;

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
public class ConfiguratorResponse extends BaseResource {

	private Long groupId;
	private Long campaignId;
	private Config config;
	private Brand branding;
	private ServiceTerm termsAndConditions;
	private Campaign campaign;
	private Set<ButtonType> buttons;
	private Set<Question> questions;

	public ConfiguratorResponse() {

	}

	public ConfiguratorResponse(RouterGroup group) {
		this.groupId = group.getId();
		this.campaignId = group.getCampaign().getId();
		this.branding = group.getBrand();
		this.termsAndConditions = group.getTermAndCondition();
		this.campaign = group.getCampaign();
		this.questions = group.getQuestions();
		this.buttons = group.getButtons();
	}

	public Long getGroupId() {
		return groupId;
	}
	
	public Long getCampaignId() {
		return campaignId;
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
}
