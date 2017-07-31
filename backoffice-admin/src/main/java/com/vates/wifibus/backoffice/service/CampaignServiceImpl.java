package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.CampaignForm;
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
		return campaignRepository.findByEnabledTrueOrderByNameDesc();
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
		if(campaignForm.getId() != null){
			campaign = campaignRepository.getOne(campaignForm.getId());
		}
		mergeItems(campaign, campaignForm);
        BeanUtils.copyProperties(campaignForm, campaign, "id", "advertisements");
        campaignRepository.save(campaign);
	}

	private void mergeItems(final Campaign campaign, CampaignForm campaignForm) {
		campaignForm.getAdvertisements().forEach(item->item.setCampaign(campaign));
		if(!CollectionUtils.isEmpty(campaign.getAdvertisements())){
			campaign.getAdvertisements().forEach(item->item.setCampaign(null));
			// Adding new items
			campaignForm.getAdvertisements().forEach(item->{
				if(item.getId() == null)
					campaign.getAdvertisements().add(item);
			});
			// Removing and updating items that already exist.
			Iterator<Advertisement> it = campaign.getAdvertisements().iterator();
			while(it.hasNext()){
				boolean exist = false;
				Advertisement adv = (Advertisement) it.next();
				for(Advertisement advForm : campaignForm.getAdvertisements()){
					if(adv.getId() != null && advForm.getId() != null && adv.getId().intValue() == advForm.getId().intValue()){
						BeanUtils.copyProperties(advForm, adv, "id");
						exist = true;
						break;
					}
				}
				if(!exist && adv.getId() != null){
					it.remove();
				}
			}
		} else {
			campaign.setAdvertisements(new LinkedHashSet<Advertisement>(campaignForm.getAdvertisements()));
		}
	}

	@Override
	public Long countByName(String name) {
		return campaignRepository.countByName(name);
	}
	
	@Override
	public Collection<Campaign> getCampaignsByDefault(boolean isDefault) {
		return campaignRepository.findByDefaulted(isDefault);
	}
}