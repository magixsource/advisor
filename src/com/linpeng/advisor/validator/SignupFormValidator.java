package com.linpeng.advisor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * Signup form validator
 * 
 * @author linpeng
 *
 */
public class SignupFormValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequired("username", "usernameMsg", "please enter username");
		validateRequired("password", "passwordMsg", "please enter password");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("username", "password");
		c.render("index.html");
	}

}
