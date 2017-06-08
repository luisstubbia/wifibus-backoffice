package com.vates.wifibus.backoffice.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.vates.wifibus.backoffice.model.Brand;
import com.vates.wifibus.backoffice.model.BrandForm;
import com.vates.wifibus.backoffice.model.PaginatorForm;
import com.vates.wifibus.backoffice.service.BrandService;
import com.vates.wifibus.backoffice.validator.BrandFormValidator;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Controller
@SessionAttributes(types = BrandForm.class)
public class BrandingController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private BrandFormValidator brandValidator;
	
    @GetMapping("/branding")
    public String getBrandingPage(Model model) {
    	Page<Brand> brands = brandService.getBrands(1, PaginatorForm.ROWS_TO_SHOW.get(0), "");
     	model.addAttribute("paginator", new PaginatorForm(brands));
    	model.addAttribute("selections", brands.getContent());
        return "branding";
    }
    
    @RequestMapping(value = "/branding", method = RequestMethod.POST)
    public String findBrandPage(PaginatorForm paginator, BindingResult result, Model model) {
    	boolean isQuery = true;
    	if(StringUtils.isEmpty(paginator.getQuery())){
    		paginator.setQuery("");
    		isQuery = true;
    	}
    	Page<Brand> brands = brandService.getBrands(paginator.getSelectedPage(), 
    			paginator.getSelectedRowsToShow(), paginator.getQuery());
    	if(brands != null && brands.getContent().size() == 1 && isQuery){
    		return "redirect:/branding/"+ brands.getContent().get(0).getId() +"/edit";
    	} else {
    		paginator.update(brands);
        	model.addAttribute("paginator", paginator);
        	model.addAttribute("selections", brands.getContent());
        	return "branding";
    	}
    }
    
    @RequestMapping("/branding/new")
	public ModelAndView getBrandNewPage() {
		ModelAndView model = new ModelAndView("createOrUpdateBrandForm");
		BrandForm brandForm = new BrandForm();
		model.addObject("brandForm", brandForm);
    	return model;
    }
    
    @RequestMapping(value = "/branding/{brandId}/edit", method = RequestMethod.GET)
    public String getBrandInfoPage(@PathVariable("brandId") Long brandId, Model model) {
    	BrandForm brandForm = new BrandForm();
    	if(null != brandId){
    		Optional<Brand> brand = brandService.getById(brandId);
    		if(brand.isPresent()){
    			BeanUtils.copyProperties(brand.get(), brandForm);
    		}
    	}
    	model.addAttribute(brandForm);
    	return "createOrUpdateBrandForm";
    }
    
    @RequestMapping(value = {"/branding/{brandId}/edit", "/branding/new"}, method = RequestMethod.POST)
    public String createBrandInfoPage(BrandForm brand, BindingResult result, Model model, 
    		@ModelAttribute("action") String action, SessionStatus status) {
        brandValidator.validate(brand, result);
        if (result.hasErrors()) {
            return "createOrUpdateBrandForm";
        } else {
        	brandService.addOrUpdateBrand(brand);
        	status.setComplete();
            return "redirect:/branding";
        }
    }
    
	@RequestMapping(value = "/branding/{id}/delete", method = RequestMethod.GET)
    public String deleteBrandPage(@PathVariable Long id) {
    	brandService.deleteById(id);
    	return "redirect:/branding";
    }
}