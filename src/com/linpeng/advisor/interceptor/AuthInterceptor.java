package com.linpeng.advisor.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.linpeng.advisor.annotation.AopIgnore;
import com.linpeng.advisor.model.User;

/**
 * Authentic or not
 * 
 * @author linpeng
 *
 */
public class AuthInterceptor implements Interceptor {

	public static final String UNIQUE_INTERCEPTOR_ID = "AUTHINTERCEPTER";

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		User loginUser = controller.getSessionAttr("loginUser");
		if (loginUser != null || isIgnoreMe(controller)) {
			ai.invoke();
		} else {
			controller.redirect("/login/login");
		}
	}

	/**
	 * Check is this should ignore
	 * 
	 * @param controller
	 * @return
	 */
	private boolean isIgnoreMe(Controller controller) {
		AopIgnore annotation = controller.getClass().getAnnotation(
				AopIgnore.class);
		if (null == annotation || null == annotation.value()) {
			return false;
		}
		for (String value : annotation.value()) {
			if (value.equalsIgnoreCase(UNIQUE_INTERCEPTOR_ID)) {
				return true;
			}
		}
		return false;
	}

}
