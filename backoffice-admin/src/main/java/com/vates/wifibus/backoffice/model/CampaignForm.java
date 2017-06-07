package com.vates.wifibus.backoffice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Campaign data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class CampaignForm {
	
	public final static String UP = "UP";
	public final static String DOWN = "DOWN";
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private String landingUrl;
	
	private Collection<AdvertisementForm<?,?>> advertisementForms =  new ArrayList<AdvertisementForm<?,?>>();
	
	private String type;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLandingUrl() {
		return landingUrl;
	}

	public void setLandingUrl(String landingUrl) {
		this.landingUrl = landingUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Collection<AdvertisementForm<?,?>> getAdvertisementForms() {
		return this.advertisementForms;
	}
	
	public void addAdvertisementFormItem(AdvertisementForm<?,?> form){
		form.setPriority(this.advertisementForms.size() + 1);
		this.advertisementForms.add(form);
	}
	
	/**
	 * Populate advertisements.
	 * 
	 * @param advertisements
	 */
	public void buildAdvertisements(Set<Advertisement> advertisements) {
		for(Advertisement adv : advertisements){
			if(adv instanceof VideoAd){
				VideoAd advVideo = (VideoAd) adv;
				VideoAdForm videoAd = new VideoAdForm();
				this.advertisementForms.add(videoAd.toForm(advVideo));
			} else if(adv instanceof BannerAd){
				BannerAd advBanner = (BannerAd) adv;
				BannerAdForm bannerAd = new BannerAdForm();
				this.advertisementForms.add(bannerAd.toForm(advBanner));
			}
		}
	}

	private void sortAdvertisementList(){
		Collections.sort((List<AdvertisementForm<?,?>>) advertisementForms,
                (ad1, ad2) -> ad1.getPriority().compareTo(ad2.getPriority()));
	}
	
	/**
	 * Reseting advertisement priority
	 */
	public void resetAdvPriority() {
		sortAdvertisementList();
		int index = 1;
		for(AdvertisementForm<?,?> adv : advertisementForms){
			adv.setPriority(index ++);
		}
	}

	/**
	 * Move priority
	 * 
	 * @param  advId
	 * @param option - UP/DOWN
	 */
	public void moveAdvPriority(int advId, String option) {
		if(!CollectionUtils.isEmpty(advertisementForms) && advId <= advertisementForms.size()){
			int newPriority = 0;
			if(UP.equals(option)){
				newPriority = advId - 1;
			}
			if(DOWN.equals(option)){
				newPriority = advId + 1;
			}
			AdvertisementForm<?,?> currentAdv = null;
			AdvertisementForm<?,?> nextAdv = null;
			for(AdvertisementForm<?, ?> adv : advertisementForms){
				if(adv.getPriority().intValue() == newPriority){
					nextAdv = adv;
				}
				if(adv.getPriority().intValue() == advId){
					currentAdv = adv;
				}
			}
			nextAdv.setPriority(advId);
			currentAdv.setPriority(newPriority);
			sortAdvertisementList();
		}
	}
}

