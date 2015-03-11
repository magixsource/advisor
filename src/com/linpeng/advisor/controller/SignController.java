package com.linpeng.advisor.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;
import com.linpeng.advisor.common.EncrypMD5;
import com.linpeng.advisor.interceptor.MenuInjectIntercepter;
import com.linpeng.advisor.model.User;
import com.linpeng.advisor.validator.SignupFormValidator;

/**
 * User Controller
 * 
 * @author linpeng
 *
 */
@ClearInterceptor
public class SignController extends Controller {

	/**
	 * Sign up
	 */
	@Before(MenuInjectIntercepter.class)
	public void index() {

	}

	/**
	 * Save user
	 */
	@Before(SignupFormValidator.class)
	public void save() {
		Integer id = getParaToInt("id", null);
		String username = getPara("username");
		String password = getPara("password");

		password = EncrypMD5.eccryptString(password);

		if (null == id) {
			new User().set("username", username).set("password", password)
					.save();
		} else {
			User.dao.findById(id).set("password", password).update();
		}

		// redirect to application home page
		redirect("/");
	}

}
