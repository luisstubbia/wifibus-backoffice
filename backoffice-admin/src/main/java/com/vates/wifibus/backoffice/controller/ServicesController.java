package com.vates.wifibus.backoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Controller
public class ServicesController {

    @GetMapping("/services")
    public String getServicesPage() {
        return "services";
    }
    
    @GetMapping("/service/create")
    public String getCreateServicePage() {
        return "service-create";
    }

}

