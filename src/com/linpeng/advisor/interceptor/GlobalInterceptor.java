package com.linpeng.advisor.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.linpeng.advisor.config.BaseConfig;

/**
 * application global interceptor<br>
 * put some app-scope info like appName
 * 
 * @author linpeng
 *
 */
public class GlobalInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		// put appName
		String appName = BaseConfig.appProperties.getProperty("app.name");
		ai.getController().setAttr("appName", appName);
		System.out.println("======"+appName);
		ai.invoke();
	}

}
