package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Modela los anuncios tipo banner.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@DiscriminatorValue("BANNER")
public class BannerAd extends Advertisement {

	@Column(name = "SOURCE_URL", nullable = false, length = 1024)
	private String backgroundImageUrl;
	
	@Column(name = "TEXT", length = 500)
	private String text;

	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	public void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
