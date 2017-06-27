package com.vates.wifibus.backoffice.api.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:app.properties")
public class ApplicationConfiguration {

	@Value("#{${config.api_keys}}")
	private Map<String,String> apiKeys;

	public Map<String, String> getApiKeys() {
		return apiKeys;
	}

}
