package com.vates.wifibus.backoffice.api.resource;

import java.util.Set;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vates.wifibus.backoffice.model.Advertisement;

/**
 * Recurso de anuncios filtrados por parametros del usuario
 * 
 * @author luis.stubbia
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Campaing extends BaseResource<Filter> {

	public static final String SESSION_LINK = "sessions";
	
	private Set<Advertisement> advertisements;

	public Campaing() {
	}
	
	public Campaing(Set<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	public Set<Advertisement> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}
	
	@Override
	public void populateLinks(Filter resource) {
		setSelf();
		
		if(resource != null){
			String sessionUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/" + SESSION_LINK + "/{mac_address}").buildAndExpand(resource.getMacAddress()).toUriString();
			addLink(SESSION_LINK + "-close", new EntityLink("POST", sessionUri));
			addLink(SESSION_LINK, new EntityLink("GET", sessionUri));
		}
	}
}