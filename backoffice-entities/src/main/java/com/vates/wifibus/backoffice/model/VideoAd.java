package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Modela los anuncios tipo video.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@DiscriminatorValue("VIDEO")
public class VideoAd  extends Advertisement {
	
	@Column(name = "SOURCE_URL", nullable = false, length = 1024)
	private String videoUrl;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}
