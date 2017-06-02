package com.vates.wifibus.backoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Controller
public class AdsController {

    @RequestMapping("/ads")
    public String getAdsPage() {
        return "ads";
    }

}
