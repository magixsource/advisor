package com.linpeng.advisor.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.linpeng.advisor.model.Dictionary;
import com.linpeng.advisor.model.Disease;
import com.linpeng.advisor.validator.DiseaseFormValidator;

/**
 * Disease Controller
 * 
 * @author linpeng
 *
 */
public class DiseaseController extends Controller {

	public static final String FIND_DISEASE_DEPT = "SELECT * FROM dictionary WHERE kind='APP_DICT_DISEASE_DEPT'";

	public void create() {
		setAttr("dictionaryList", Dictionary.dao.find(FIND_DISEASE_DEPT));
	}

	public void modify() {
		String sid = getPara(0);
		setAttr("disease", Disease.dao.findById(sid));
		setAttr("dictionaryList", Dictionary.dao.find(FIND_DISEASE_DEPT));
		render("create.html");
	}

	public void view() {
		String sid = getPara(0);
		setAttr("disease", Disease.dao.findById(sid));
		setAttr("dictionaryList", Dictionary.dao.find(FIND_DISEASE_DEPT));
	}

	@Before(DiseaseFormValidator.class)
	public void save() {
		String sid = getPara("sid", null);
		String name = getPara("name");
		String dept = getPara("dept");
		String summary = getPara("summary");
		String treatment = getPara("treatment");

		if (null == sid) {
			new Disease().set("name", name).set("dept", dept)
					.set("summary", summary).set("treatment", treatment).save();
		} else {
			Disease.dao.findById(sid).set("name", name).set("dept", dept)
					.set("summary", summary).set("treatment", treatment)
					.update();
		}

		redirect("/disease/list");
	}

	public void delete() {
		String sid = getPara(0);
		Disease.dao.deleteById(sid);
		redirect("/disease/list");
	}
	
	public void list() {
		String name= getPara("name");
		int pageNumber = getParaToInt("page", getParaToInt(0,1));
		int pageSize = getParaToInt("pagesize", 10);
		
		if(null!=name && name.length()>0){
			setAttr("page", Disease.dao.paginate(pageNumber, pageSize, "select *",
					" from disease where name like '%"+name+"%'"));
			setAttr("name",name);
		}else{
			setAttr("page", Disease.dao.paginate(pageNumber, pageSize, "select *",
					" from disease"));
		}
		
		setAttr("dictionaryList", Dictionary.dao.find(FIND_DISEASE_DEPT));
		render("list.html");
	}
}
