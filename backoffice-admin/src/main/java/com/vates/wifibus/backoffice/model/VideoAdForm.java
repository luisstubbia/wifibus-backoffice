package com.vates.wifibus.backoffice.model;

import org.springframework.beans.BeanUtils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: VideoAd data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class VideoAdForm extends AdvertisementForm<VideoAd, VideoAdForm> {
	
	private String videoUrl;

	public final String type = "Video";
	
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public VideoAd toModel(Advertisement advModel) {
		VideoAd adv = new VideoAd();
		if(advModel != null && advModel instanceof VideoAd){
			adv = (VideoAd) advModel;
		}
		BeanUtils.copyProperties(this, adv, "id");
		return adv;
	}

	@Override
	public VideoAdForm toForm(VideoAd model) {
		BeanUtils.copyProperties(model, this);
		return this;
	}
}

