package com.linpeng.advisor.controller;

import com.jfinal.core.Controller;
import com.linpeng.advisor.model.Disease;

/**
 * Disease Controller
 * 
 * @author linpeng
 *
 */
public class DiseaseController extends Controller {

	public void create() {

	}

	public void modify() {
		String sid = getPara("sid");
		setAttr("disease", Disease.dao.findById(sid));
		render("create.html");
	}

	public void view() {
		String sid = getPara(0);
		setAttr("disease", Disease.dao.findById(sid));
	}

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

	public void remove() {
		String sid = getPara("sid");
		Disease.dao.deleteById(sid);
		redirect("/disease/list");
	}

	public void list() {
		int pageNumber = getParaToInt(0, 1);
		int pageSize = getParaToInt("pagesize", 10);

		setAttr("page", Disease.dao.paginate(pageNumber, pageSize, "select *",
				" from disease"));

		render("list.html");
	}
}
