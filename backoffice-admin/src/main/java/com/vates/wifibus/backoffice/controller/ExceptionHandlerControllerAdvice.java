package com.vates.wifibus.backoffice.controller;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Luis Stubbia
 *
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException e, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", e);
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("exception", e);
        return e.getMessage();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("exception", throwable);
        return "exception";
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnknownResourceException(RuntimeException ex) {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("exception", ex);
    	mav.setViewName("exception");
    	return mav;
    }
    
}
