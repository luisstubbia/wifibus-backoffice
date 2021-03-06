package com.vates.wifibus.backoffice.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.vates.wifibus.backoffice.model.Advertisement;
import com.vates.wifibus.backoffice.model.AdvertisementType;
import com.vates.wifibus.backoffice.model.Campaign;
import com.vates.wifibus.backoffice.model.CampaignForm;
import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.service.CampaignService;
import com.vates.wifibus.backoffice.service.SegmentService;
import com.vates.wifibus.backoffice.validator.CampaignFormValidator;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Controller
@SessionAttributes(types = CampaignForm.class)
public class CampaignsController {
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private SegmentService segmentService;

	@Autowired
	private CampaignFormValidator campaignValidator;
	
    @GetMapping("/campaigns")
    public String getCampaignPage(Model model) {
    	Page<Campaign> campaigns = campaignService.getCampaigns(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(campaigns));
    	model.addAttribute("selections", campaigns.getContent());
        return "campaigns";
    }
    
    @RequestMapping(value = "/campaigns", method = RequestMethod.POST)
    public String findCampaignPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = true;
    	}
    	Page<Campaign> campaigns = campaignService.getCampaigns(paginator.getSelectedPage(), 
    			paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if(campaigns != null && campaigns.getContent().size() == 1 && isQuery){
    		return "redirect:/campaigns/"+ campaigns.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(campaigns);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", campaigns.getContent());
        	return "campaigns";
    	}
    }
    
    @RequestMapping("/campaigns/new")
	public String getCampaignNewPage(Model model) {
    	CampaignForm campaignForm = new CampaignForm();
		model.addAttribute("campaignForm", campaignForm);
    	return "createOrUpdateCampaignForm";
    }
    
    @RequestMapping(value = "/campaigns/{campaignId}/edit", method = RequestMethod.GET)
    public String getCampaignInfoPage(@PathVariable("campaignId") Long campaignId, Model model) {
    	CampaignForm campaignForm = new CampaignForm();
    	if(null != campaignId){
    		Optional<Campaign> campaign = campaignService.getById(campaignId);
    		if(campaign.isPresent()){
    			Campaign cmp = campaign.get();
    			BeanUtils.copyProperties(cmp, campaignForm, "advertisements");
    			if(!CollectionUtils.isEmpty(cmp.getAdvertisements())){
    				campaignForm.getAdvertisements().addAll(cmp.getAdvertisements());
    			}
    		}
    	}
    	model.addAttribute(campaignForm);
    	model.addAttribute("segments", segmentService.getAll());
    	return "createOrUpdateCampaignForm";
    }
    
    @RequestMapping(value = {"/campaigns/{campaignId}/edit", "/campaigns/new"}, method = RequestMethod.POST)
    public String createCampaignInfoPage(CampaignForm campaign, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, @ModelAttribute("removeAdv") String advId, 
    		@ModelAttribute("moveUpAdv") String upAdvId, @ModelAttribute("moveDownAdv") String downAdvId, 
    		SessionStatus status) {
    	model.addAttribute("segments", segmentService.getAll());
    	if(!StringUtils.isEmpty(advId)){
        	removeAdv(campaign, Integer.parseInt(advId));
        	return "createOrUpdateCampaignForm";
        }
        if(!StringUtils.isEmpty(upAdvId)){
        	campaign.moveAdvPriority(Integer.parseInt(upAdvId), CampaignForm.UP);
        	return "createOrUpdateCampaignForm";
        }
        if(!StringUtils.isEmpty(downAdvId)){
        	campaign.moveAdvPriority(Integer.parseInt(downAdvId), CampaignForm.DOWN);
        	return "createOrUpdateCampaignForm";
        }
        campaignValidator.validate(campaign, result);       
        if (result.hasErrors()) {
            return "createOrUpdateCampaignForm";
        } else {
        	campaignService.addOrUpdateCampaign(campaign);
        	status.setComplete();
            return "redirect:/campaigns";
        }
    }

	@RequestMapping(value = {"/campaigns/{campaignId}/edit", "/campaigns/new"}, method = RequestMethod.POST, params="action=addAdv")
    public String addAdvertisementView(CampaignForm campaign, BindingResult result, Model model, SessionStatus status) {
    	AdvertisementType adv = AdvertisementType.lookupByName(campaign.getType());
    	campaign.addAdvertisementItem(adv.getInstance());
    	model.addAttribute("segments", segmentService.getAll());
    	return "createOrUpdateCampaignForm";
    }
    
	@RequestMapping(value = "/campaigns/{id}/delete", method = RequestMethod.GET)
    public String deleteBrandPage(@PathVariable Long id) {
    	campaignService.deleteById(id);
    	return "redirect:/campaigns";
    }
	
    
    /**
     * Remove adv element from campaign form.
     * @param campaign
     * @param advId
     */
    private void removeAdv(CampaignForm campaign, int advId) {
		for(Advertisement adv : campaign.getAdvertisements()){
			if(adv.getPriority().intValue() == advId){
				campaign.getAdvertisements().remove(adv);
				adv.setCampaign(null);
				campaign.resetAdvPriority();
				break;
			}
		}
    }
}
