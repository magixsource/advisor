package com.linpeng.advisor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * Dictionary kind form validator
 * 
 * @author linpeng
 *
 */
public class DictionaryKindFormValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("title", "titleMsg", "please enter title");
		validateRequiredString("kind", "kindMsg", "please enter kind");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("id");
		String title = c.getAttrForStr("title");
		String kind = c.getAttrForStr("kind");

		if (null != title) {
			c.keepPara("title");
		}

		if (null != kind) {
			c.keepPara("kind");
		}
		c.render("create.html");
	}

}
