package com.vates.wifibus.backoffice.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:app.properties")
public class ApplicationConfiguration {

	@Value("${config.api_keys.facebook}")
	private String facebookAPI;

	@Value("${config.api_keys.google}")
	private String googleAPI;
	
	@Value("${config.api_keys.mail}")
	private String mailAPI;
	
	public String getFacebookAPI() {
		return facebookAPI;
	}

	public String getGoogleAPI() {
		return googleAPI;
	}

	public String getMailAPI() {
		return mailAPI;
	}
}
