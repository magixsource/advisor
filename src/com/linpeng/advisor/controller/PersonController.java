package com.linpeng.advisor.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.linpeng.advisor.common.StringUtils;
import com.linpeng.advisor.model.Dictionary;
import com.linpeng.advisor.model.Disease;
import com.linpeng.advisor.model.Person;
import com.linpeng.advisor.model.User;

/**
 * 
 * @author linpeng
 *
 */
public class PersonController extends Controller {
	public static final String FIND_RELATIONSHIP_TYPE = "SELECT * FROM dictionary WHERE kind='APP_DICT_RELATIONSHIP_TYPE'";

	/**
	 * Create a person information
	 */
	public void create() {
		User loginUser = getSessionAttr("loginUser");
		setAttr("userid", loginUser.getInt("id"));

		setAttr("relationships", Dictionary.dao.find(FIND_RELATIONSHIP_TYPE));
		setAttr("diseases", Disease.dao.find("select * from disease"));
	}

	public void update() {
		// get personid
		int personId = getParaToInt(0);

		User loginUser = getSessionAttr("loginUser");
		setAttr("userid", loginUser.getInt("id"));
		setAttr("person", Person.dao.findById(personId));
		setAttr("relationships", Dictionary.dao.find(FIND_RELATIONSHIP_TYPE));
		setAttr("diseases", Disease.dao.find("select * from disease"));

		render("create.html");
	}

	public void list() {
		String name= getPara("name");
		int pageNumber = getParaToInt("page", getParaToInt(0,1));
		int pageSize = getParaToInt("pagesize", 10);

		User loginUser = getSessionAttr("loginUser");
		int userId = loginUser.getInt("id");

		if(null!=name && name.length()>0){
			setAttr("page", Person.dao.paginate(pageNumber, pageSize, "select *",
					" from person where name like '%"+name+"%' and userid = " + userId));
			setAttr("name",name);
		}else{
			setAttr("page", Person.dao.paginate(pageNumber, pageSize, "select *",
					" from person where userid = " + userId));
		}
		
		setAttr("relationships", Dictionary.dao.find(FIND_RELATIONSHIP_TYPE));

		render("list.html");
	}

	@Before(Tx.class)
	public void save() {
		String id = getPara("id", null);
		String userId = getPara("userid");
		String name = getPara("name");
		String relationship = getPara("relationship");

		String[] diseaseArr = getParaValues("disease");
		if (diseaseArr == null) {
			diseaseArr = new String[] { "" };
		}

		if (null == id) {
			new Person().set("name", name).set("userid", userId)
					.set("relationship", relationship)
					.set("disease", StringUtils.array2string(diseaseArr))
					.save();
		} else {
			Person.dao.findById(id).set("name", name)
					.set("relationship", relationship)
					.set("disease", StringUtils.array2string(diseaseArr))
					.update();
		}

		redirect("/person/list");
	}

}
