package com.linpeng.advisor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * Disease Validator
 * 
 * @author linpeng
 *
 */
public class DiseaseFormValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("name", "nameMsg", "please enter name");
		validateRequiredString("dept", "deptMsg", "please choise dept");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("sid");
		c.render("create.html");
	}

}
