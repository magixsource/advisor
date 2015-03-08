package com.linpeng.advisor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * Dictionary form validator
 * 
 * @author linpeng
 *
 */
public class DictionaryFormValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("title", "titleMsg", "please enter title");
		validateRequiredString("code", "codeMsg", "please enter code");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("id");
		c.keepPara("kindid");
		String title = c.getAttrForStr("title");
		String code = c.getAttrForStr("code");

		if (null != title) {
			c.keepPara("title");
		}

		if (null != code) {
			c.keepPara("code");
		}
		c.render("add.html");
	}

}
