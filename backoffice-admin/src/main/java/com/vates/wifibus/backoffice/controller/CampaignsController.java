package com.vates.wifibus.backoffice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Controller
public class CampaignsController {

	private static final Logger logger = LoggerFactory.getLogger(CampaignsController.class);
	
//    @Autowired
//    private CampaignService campaignService;

    /**
     * @return
     */
    @RequestMapping("/campaigns")
    public ModelAndView getCampaignsPage() {
//        return new ModelAndView("campaigns", "campaigns", campaignService.getAll());
        return new ModelAndView("campaigns");
    }
    
}
