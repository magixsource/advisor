package com.linpeng.advisor.interceptor;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.linpeng.advisor.model.Menu;
import com.linpeng.advisor.model.User;

/**
 * Inject Menu in application
 * 
 * @author linpeng
 *
 */
public class MenuInjectIntercepter implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		User loginUser = controller.getSessionAttr("loginUser");

		List<Menu> applicationMenus = new ArrayList<Menu>();

		if (null != loginUser && loginUser.get("username").equals("admin")) {
			applicationMenus = Menu.dao
					.find("select * from menu order by orderby");
		} else {
			applicationMenus = Menu.dao
					.find("select * from menu where admin is null order by orderby");
		}
		controller.setAttr("applicationMenus", applicationMenus);
		ai.invoke();
	}

}
