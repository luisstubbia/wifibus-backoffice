package com.vates.wifibus.backoffice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	
	private Collection<Advertisement> advertisements =  new ArrayList<Advertisement>();
	
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
	
	public Collection<Advertisement> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Collection<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}
	
	public void addAdvertisementItem(Advertisement adv){
		adv.setPriority(this.advertisements.size() + 1);
		this.advertisements.add(adv);
	}
	
	private void sortAdvertisementList(){
		Collections.sort((List<Advertisement>) advertisements,
                (ad1, ad2) -> ad1.getPriority().compareTo(ad2.getPriority()));
	}
	
	/**
	 * Reseting advertisement priority
	 */
	public void resetAdvPriority() {
		sortAdvertisementList();
		int index = 1;
		for(Advertisement adv : advertisements){
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
		if(!CollectionUtils.isEmpty(advertisements) && advId <= advertisements.size()){
			int newPriority = 0;
			if(UP.equals(option)){
				newPriority = advId - 1;
			}
			if(DOWN.equals(option)){
				newPriority = advId + 1;
			}
			Advertisement currentAdv = null;
			Advertisement nextAdv = null;
			for(Advertisement adv : advertisements){
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