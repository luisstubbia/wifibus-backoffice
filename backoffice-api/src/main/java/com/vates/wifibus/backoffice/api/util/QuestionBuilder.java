package com.vates.wifibus.backoffice.api.util;

import org.springframework.stereotype.Component;

import com.vates.wifibus.backoffice.model.QuestionType;
import com.vates.wifibus.backoffice.model.SegmentItem;

/**
 * Question builder utility
 * 
 * @author luis.stubbia
 *
 */
@Component
public abstract class QuestionBuilder {

	private static final String PATH = "com.vates.wifibus.backoffice.api.util.";
	private static final String SUFFIX = "Builder";
	
	public static QuestionBuilder builder(QuestionType questionType) throws Exception {
		Class<?> c = Class.forName(PATH + questionType.name() + SUFFIX);
		return (QuestionBuilder) c.newInstance();
	}
	
	/**
	 * Metodo principal que determina el validador de la respuesta.
	 * @param item
	 * @param value
	 * @return
	 */
	public abstract boolean validAnswer(SegmentItem item, String value);
}
