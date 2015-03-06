package com.linpeng.advisor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * login validator,required username & password
 * 
 * @author linpeng
 *
 */
public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("username", "usernameMsg",
				"please enter your account");
		validateRequiredString("password", "passwordMsg",
				"please enter your password");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("username");
		c.render("login.html");
	}

}
