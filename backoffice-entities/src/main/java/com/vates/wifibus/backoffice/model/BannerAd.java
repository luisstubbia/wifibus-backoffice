package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

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
	@NotNull
	private String backgroundImageUrl;
	
	@Column(name = "TEXT", nullable = false, length = 500)
	@NotNull
	private String text;
	
}
