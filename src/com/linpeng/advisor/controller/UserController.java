package com.linpeng.advisor.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.linpeng.advisor.common.StringUtils;
import com.linpeng.advisor.model.Disease;
import com.linpeng.advisor.model.User;

public class UserController extends Controller {

	/**
	 * User base info
	 */
	public void update() {
		User loginUser = getSessionAttr("loginUser");
		setAttr("user", User.dao.findById(loginUser.getInt("id")));
		setAttr("diseases", Disease.dao.find("select * from disease"));
	}

	@Before(Tx.class)
	public void save() {

		User loginUser = getSessionAttr("loginUser");
		String[] diseaseArr = getParaValues("disease");
		if (diseaseArr == null) {
			diseaseArr = new String[] { "" };
		}

		User.dao.findById(loginUser.getInt("id"))
				.set("disease", StringUtils.array2string(diseaseArr)).update();

		redirect("/user/update");
	}

}
