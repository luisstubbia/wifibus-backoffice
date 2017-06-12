package com.vates.wifibus.backoffice.model;

import org.springframework.beans.BeanUtils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: BannerAdForm data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class BannerAdForm extends AdvertisementForm<BannerAd, BannerAdForm> {
	
	private String backgroundImageUrl;
	
	private String text;

	public final String type = "Banner";
	
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

	@Override
	public BannerAd toModel(Advertisement advModel) {
		BannerAd adv = new BannerAd();
		if(advModel != null && advModel instanceof BannerAd){
			adv = (BannerAd) advModel;
		}
		BeanUtils.copyProperties(this, adv, "id");
		return adv;
	}

	@Override
	public BannerAdForm toForm(BannerAd model) {
		BeanUtils.copyProperties(model, this);
		return this;
	}
}

