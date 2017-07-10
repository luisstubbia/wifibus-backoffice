package com.vates.wifibus.backoffice.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vates.wifibus.backoffice.api.config.ApplicationConfiguration;
import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.resource.CampaignResponse;
import com.vates.wifibus.backoffice.api.resource.ErrorCode;
import com.vates.wifibus.backoffice.api.util.ProfileMapper;
import com.vates.wifibus.backoffice.api.util.QuestionBuilder;
import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.Profile;
import com.vates.wifibus.backoffice.model.ProfileValue;
import com.vates.wifibus.backoffice.model.Segment;
import com.vates.wifibus.backoffice.model.SegmentItem;
import com.vates.wifibus.backoffice.repository.CampaignRepository;
import com.vates.wifibus.backoffice.repository.SegmentRepository;

/**
 * 
 * @author luis.stubbia
 *
 */
@Service
public class CampaignServiceImpl implements CampaignService {
	
	private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);
	private static final String LOG_KEY = "FILTRO:";
	private static StringBuffer LOG_STIRNG;
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private SegmentRepository segmentRepository;
	
	@Autowired
	private ApplicationConfiguration app;
	
	@Override
	public CampaignResponse filterAdvertisements(Long campaignId, Profile profile) throws Exception {
		CampaignResponse campaignReq = new CampaignResponse();
		Campaign campaign = applyFilters(campaignId, profile);
		if(campaign != null){
			campaignReq = new CampaignResponse(campaign, profile.getId());
		} else {
			campaignReq.addError(new BussinesError(ErrorCode.ADVERTISEMENT_NOT_FOUND));
		}
		return campaignReq;
	}

	@Override
	public CampaignResponse getCampaign(Long campaignId) {
		CampaignResponse campaignReq = new CampaignResponse();
		if (campaignId != null) {
			Campaign camp = campaignRepository.findOne(campaignId);
			if(camp != null){
				campaignReq = new CampaignResponse(camp, null);
			} else {
				campaignReq.addError(new BussinesError(ErrorCode.CAMPAIGN_NOT_FOUND));
			}
		} else {
			campaignReq.addError(new BussinesError(ErrorCode.BAD_INPUT));
		}
		return campaignReq;
	}
	
	/**
	 * Filter advertisements according to the profile.
	 * @param campaignId
	 * @param profile
	 * @return list of advs
	 */
	private Campaign applyFilters(Long campaignId, Profile profile) throws Exception {
		LOG_STIRNG = new StringBuffer();
		LOG_STIRNG.append(LOG_KEY).append(" Campaña/Perfil: ").append(campaignId + "/" +profile.getId()).append("\n");
		List<Segment> segments = segmentRepository.getSegmentByCampaign(campaignId);
		List<Long> segmentIds;
		segmentIds = filterByProfile(segments, profile);
		LOG_STIRNG.append(LOG_KEY).append(" Segmentos filtrados: " + segmentIds).append("\n");
		Campaign campaign = campaignRepository.findOne(campaignId);
		if(campaign != null){
			Iterator<Advertisement> advIt = campaign.getAdvertisements().iterator();
			while(advIt.hasNext()){
				Advertisement adv = advIt.next();
				if(mustRemoveAdv(adv, segmentIds)) {
					advIt.remove();
				}
			}
			logger.info(LOG_STIRNG.toString());
			return campaign;
		}
		logger.info(LOG_STIRNG.toString());
		return null;
	}

	/**
	 * This method validates if the advertisement must be removed or not.
	 * 
	 * @param adv
	 * @param segmentIds
	 * @return boolean
	 */
	private boolean mustRemoveAdv(Advertisement adv, List<Long> segmentIds) {
		Date today = new Date();
		boolean validDate = true;
		LOG_STIRNG.append(LOG_KEY).append(" Anuncio: " + adv.getName()).append("\n");
		LOG_STIRNG.append(LOG_KEY).append(" Fechas start - end ").append(adv.getStartDate() + "/" + adv.getEndDate()).append("\n");
		if(adv.getStartDate().equals(today) || adv.getStartDate().before(today)) {
			if(adv.getEndDate() != null){
				if(!adv.getEndDate().equals(today) && !adv.getEndDate().after(today)) {
					validDate = false;
				}
			}
		} else {
			validDate = false;
		}
		LOG_STIRNG.append(LOG_KEY).append(" fecha valida: " + validDate).append("\n");
		if(adv.getSegment() == null) {
			LOG_STIRNG.append(LOG_KEY).append(">> Anuncio valido por defecto: " + adv.getName()).append("\n");
			return !validDate;
		} else if (!CollectionUtils.isEmpty(segmentIds) && segmentIds.contains(adv.getSegment().getId())) {
			LOG_STIRNG.append(LOG_KEY).append(">> Anuncio valido: " + adv.getName()).append("\n");
			return !validDate;
		} else {
			LOG_STIRNG.append(LOG_KEY).append("<< Anuncio invalido >> " + adv.getName()).append("\n");
		}
		return true;
	}

	/**
	 * Get valid segments.
	 * 
	 * @param segments
	 * @param profile
	 * @param campaign
	 * @return segment list
	 * @throws Exception
	 */	
	private List<Long> filterByProfile(List<Segment> segments, Profile profile) throws Exception {
		LOG_STIRNG.append(LOG_KEY).append(" Perfil: " + profile.getLoginSource()).append("\n");
		ProfileMapper mapper = ProfileMapper.getMapper(profile);
		mapper.setApp(app);
		List<Long> segmentIds = new ArrayList<Long>();
		Iterator<Segment> segIt = segments.iterator();
		while(segIt.hasNext()){
			Segment seg = segIt.next();
			boolean validSegment = true;
			LOG_STIRNG.append(LOG_KEY).append(" Seg-item: " + seg.getId()).append("\n");
			for(SegmentItem item : seg.getItems()){
				ProfileValue answer = mapper.getValue(item.getQuestion(), profile);
				if(answer != null){
					QuestionBuilder builder = QuestionBuilder.builder(item.getQuestion().getType());
					LOG_STIRNG.append(LOG_KEY).append(" Pregunta: " + item.getQuestion().getName()).append(" - " + item.getQuestion().getAnswers()).append("\n");
					LOG_STIRNG.append(LOG_KEY).append(" Operador: " + item.getOperator()).append("\n");
					LOG_STIRNG.append(LOG_KEY).append(" Respuesta esperada: " + item.getValue()).append("\n");
					LOG_STIRNG.append(LOG_KEY).append(" Respuesta actual: " + answer.getValue()).append("\n");
					if(!builder.validAnswer(item, answer.getValue())){
						LOG_STIRNG.append(LOG_KEY).append("<< Respuesta invalida >>").append("\n");
						validSegment = false;
						break;
					}
				} else {
					validSegment = false;
				}
			}
			if(!validSegment){
				segIt.remove();
			} else {
				segmentIds.add(seg.getId());
			}
		}
		return segmentIds;
	}
}
