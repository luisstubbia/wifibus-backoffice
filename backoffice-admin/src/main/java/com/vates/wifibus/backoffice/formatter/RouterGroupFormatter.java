package com.vates.wifibus.backoffice.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.vates.wifibus.backoffice.model.RouterGroup;

/**
 * 
 * @author Luis Stubbia
 *
 */
public class RouterGroupFormatter implements Formatter<RouterGroup> {

	@Override
	public String print(RouterGroup group, Locale locale) {
		return (group != null ? group.getId().toString() : "");
	}

	@Override
	public RouterGroup parse(String id, Locale locale) throws ParseException {
		RouterGroup group = new RouterGroup();
		group.setId(Long.valueOf(id));
		return group;
	}
	
}
