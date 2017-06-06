package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.AdvertisementForm;
import com.vates.wifibus.backoffice.model.BannerAd;
import com.vates.wifibus.backoffice.model.BannerAdForm;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.CampaignForm;
import com.vates.wifibus.backoffice.model.VideoAd;
import com.vates.wifibus.backoffice.model.VideoAdForm;
import com.vates.wifibus.backoffice.repository.CampaignRepository;

/**
 * Service: Campaign service, used to handle Campaign info.
 * 
 * @author Luis Stubbia
 *
 */
@Service
public class CampaignServiceImpl implements CampaignService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);
    
	@Autowired
	private CampaignRepository campaignRepository;

	@Override
	public Optional<Campaign> getById(long id) {
		return Optional.ofNullable(campaignRepository.findOne(id));
	}

	@Override
	public Optional<Campaign> getByName(String campaignName) {
    	return campaignRepository.findOneByName(campaignName);
	}
	
	@Override
	public Collection<Campaign> getAll() {
		return campaignRepository.findAll(new Sort("name"));
	}

	@Override
	public Campaign save(Campaign campaign) {
		return campaignRepository.save(campaign);
	}

	@Override
	public void deleteById(long id) {
		logger.debug("Deleting Campaign by id={}", id);
		campaignRepository.deleteById(id);
	}

	@Override
	public Page<Campaign> getCampaigns(Integer pageNumber, Integer pageSize, String searchText) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
	        return campaignRepository.findByNameContainsAllIgnoreCase(searchText, pageRequest);
	}

	@Override
	public void addOrUpdateCampaign(CampaignForm campaignForm) {
		Campaign campaign = new Campaign();
        BeanUtils.copyProperties(campaignForm, campaign);
        copyAdvertisementProperties(campaignForm.getAdvertisementForms(), campaign);
        campaignRepository.save(campaign);
	}

	/**
	 * Populating final Advertisements.
	 * 
	 * @param advertisementForms
	 * @param campaign
	 */
	private void copyAdvertisementProperties(Collection<AdvertisementForm<?,?>> advertisementForms, Campaign campaign) {
		Set<Advertisement> advertisements = new HashSet<Advertisement>(); 
		for(AdvertisementForm<?,?> adv : advertisementForms){
			if(adv instanceof VideoAdForm){
				VideoAdForm advVideo = (VideoAdForm) adv;
				VideoAd videoAd = advVideo.toModel();
				videoAd.setCampaign(campaign);
				advertisements.add(videoAd);
			} else if (adv instanceof BannerAdForm){
				BannerAdForm advBanner = (BannerAdForm) adv;
				BannerAd bannerAd = advBanner.toModel();
				bannerAd.setCampaign(campaign);
				advertisements.add(bannerAd);
			}
		}
		campaign.setAdvertisements(advertisements);
	}

	@Override
	public Long countByName(String name) {
		return campaignRepository.countByName(name);
	}
}