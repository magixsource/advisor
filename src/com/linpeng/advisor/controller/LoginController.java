package com.linpeng.advisor.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;
import com.linpeng.advisor.common.EncrypMD5;
import com.linpeng.advisor.model.User;
import com.linpeng.advisor.validator.LoginValidator;

/**
 * application login & logout controller
 * 
 * @author linpeng
 *
 */
@ClearInterceptor
public class LoginController extends Controller {

	@Before(LoginValidator.class)
	public void login() {
		String username = getPara("username");
		String password = getPara("password");
		User user = User.dao.findFirst(
				"select * from user where username =? and password = ?",
				username, EncrypMD5.eccryptString(password));
		if (null != user) {
			setSessionAttr("loginUser", user);
			if (username.equalsIgnoreCase("admin")) {
				redirect("/dashboard");
			} else {
				redirect("/");
			}
		} else {
			setAttr("loginErrorMsg", "username or password error!");
		}

	}

	public void logout() {
		setAttr("loginMsg", "You have logout success !");
		render("login");
	}
}
