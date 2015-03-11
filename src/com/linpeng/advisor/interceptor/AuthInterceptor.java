package com.linpeng.advisor.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.linpeng.advisor.controller.IndexController;
import com.linpeng.advisor.model.User;

/**
 * Authentic or not
 * 
 * @author linpeng
 *
 */
public class AuthInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		User loginUser = controller.getSessionAttr("loginUser");
		if (loginUser != null || (controller instanceof IndexController)) {
			ai.invoke();
		} else {
			controller.redirect("/login/login");
		}
	}

}
