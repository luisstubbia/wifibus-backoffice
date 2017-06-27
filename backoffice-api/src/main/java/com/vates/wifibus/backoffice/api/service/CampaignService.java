package com.vates.wifibus.backoffice.api.service;

import com.vates.wifibus.backoffice.api.resource.Campaing;
import com.vates.wifibus.backoffice.api.resource.Filter;

/**
 * Servicio para obtener los anuncios, de acuerdo a la campaña seleccionada y los filtros aplicados.
 * 
 * @author luis.stubbia
 *
 */
public interface CampaignService {

	/**
	 * Busca los anuncios aplicables a los filtros enviados.
	 * @param campaignId 
	 * 
	 * @param filters
	 * @return Campaign resource
	 */
	Campaing getAdvertisements(Long campaignId, Filter filter);

	/**
	 * Devuelve la informacion de una campaña, sin filtros.
	 * @param campaignId
	 * @return campaign
	 */
	Campaing getCampaign(Long campaignId);
}
