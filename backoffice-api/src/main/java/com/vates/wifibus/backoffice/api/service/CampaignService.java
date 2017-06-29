package com.vates.wifibus.backoffice.api.service;

import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.resource.FilterRequest;

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
	 * @param profileId
	 * @param filters
	 * @return Campaign resource
	 */
	CampaignResponse filterAdvertisements(Long campaignId, Long profileId, FilterRequest filter);

	/**
	 * Devuelve la informacion de una campaña, sin filtros.
	 * @param campaignId
	 * @return campaign
	 */
	CampaignResponse getCampaign(Long campaignId);
}
