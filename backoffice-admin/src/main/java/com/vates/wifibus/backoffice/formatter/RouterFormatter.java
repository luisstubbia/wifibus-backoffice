package com.vates.wifibus.backoffice.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.vates.wifibus.backoffice.model.Router;

/**
 * 
 * @author Luis Stubbia
 *
 */
public class RouterFormatter implements Formatter<Router> {

	@Override
	public String print(Router router, Locale locale) {
		return (router != null ? router.getId().toString() : "");
	}

	@Override
	public Router parse(String id, Locale locale) throws ParseException {
		Router router = new Router();
		router.setId(Long.valueOf(id));
		return router;
	}
	
}