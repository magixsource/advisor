package com.linpeng.advisor.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.linpeng.advisor.common.StringUtils;
import com.linpeng.advisor.model.Dictionary;
import com.linpeng.advisor.model.Principle;

/**
 * Principle Controller
 * 
 * @author linpeng
 *
 */
public class PrincipleController extends Controller {
	public static final String FIND_FOOD_INGRED = "SELECT * FROM dictionary WHERE kind='APP_DICT_INGRED'";

	public void create() {
		// get disease id
		int diseaseId = getParaToInt(0);
		setAttr("diseaseid", diseaseId);
		setAttr("dictionaryIngredients", Dictionary.dao.find(FIND_FOOD_INGRED));
	}

	public void modify() {
		// get id
		int id = getParaToInt(0);
		Principle principle = Principle.dao.findById(id);
		
		int diseaseId = principle.getInt("disease_id");
		setAttr("diseaseid", diseaseId);
		
		setAttr("principle", principle);
		setAttr("dictionaryIngredients", Dictionary.dao.find(FIND_FOOD_INGRED));
		render("create.html");
	}

	public void view() {
		int id = getParaToInt(0);
		setAttr("principle", Principle.dao.findById(id));
		setAttr("dictionaryIngredients", Dictionary.dao.find(FIND_FOOD_INGRED));
	}

	public void show() {
		int diseaseId = getParaToInt(0);
		Principle principle = Principle.dao.findFirst(
				"select * from principle where disease_id = ?", diseaseId);
		if (null == principle) {
			redirect("/principle/create/" + diseaseId);
		} else {
			redirect("/principle/modify/" + principle.getInt("id"));
		}
	}

	@Before(Tx.class)
	public void save() {
		int diseaseId = getParaToInt("diseaseid");
		Integer id = getParaToInt("id", null);
		String[] ruleMore = getParaValues("rule_more");
		String[] ruleLess = getParaValues("rule_less");
		String[] ruleNo = getParaValues("rule_no");

		if (null == id) {
			new Principle().set("disease_id", diseaseId)
					.set("rule_more", StringUtils.array2string(ruleMore))
					.set("rule_less", StringUtils.array2string(ruleLess))
					.set("rule_no", StringUtils.array2string(ruleNo)).save();
		} else {
			Principle.dao.findById(id)
					.set("rule_more", StringUtils.array2string(ruleMore))
					.set("rule_less", StringUtils.array2string(ruleLess))
					.set("rule_no", StringUtils.array2string(ruleNo)).update();
		}

		redirect("/principle/index");
	}

}
