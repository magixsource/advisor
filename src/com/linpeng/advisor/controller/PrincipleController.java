package com.linpeng.advisor.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.linpeng.advisor.common.Constant;
import com.linpeng.advisor.common.Constant.PRINCIPLE_ACTION;
import com.linpeng.advisor.common.Constant.PRINCIPLE_ADVERB;
import com.linpeng.advisor.common.Constant.PRINCIPLE_CAUSE_TYPE;
import com.linpeng.advisor.common.Constant.PRINCIPLE_TARGET_TYPE;
import com.linpeng.advisor.common.Constant.PRINCIPLE_TYPE;
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
		int diseaseId = getParaToInt(0);
		List<Principle> principles = Principle.dao.find(
				"select * from principle where type=? and cause_type = ? and cause_id = ?",
				Constant.PRINCIPLE_TYPE.HEALTH.value, Constant.PRINCIPLE_CAUSE_TYPE.DISEASE.value, diseaseId);

		setAttr("diseaseid", diseaseId);

		List<Integer> morePrinciples = new ArrayList<Integer>(), lessPrinciples = new ArrayList<Integer>(),
				noPrinciples = new ArrayList<Integer>();
		for (Principle principle : principles) {
			int adverb = principle.getInt("adverb");
			int targetId = principle.getInt("target_id");
			if (PRINCIPLE_ADVERB.MORE.value == adverb) {
				morePrinciples.add(targetId);
			} else if (PRINCIPLE_ADVERB.LESS.value == adverb) {
				lessPrinciples.add(targetId);
			} else if (PRINCIPLE_ADVERB.NO.value == adverb) {
				noPrinciples.add(targetId);
			}
		}

		if (morePrinciples != null && !morePrinciples.isEmpty()) {
			setAttr("morePrinciples",
					StringUtils.array2string(morePrinciples.toArray(new Integer[morePrinciples.size()])));
		}
		if (lessPrinciples != null && !lessPrinciples.isEmpty()) {
			setAttr("lessPrinciples",
					StringUtils.array2string(lessPrinciples.toArray(new Integer[lessPrinciples.size()])));
		}
		if (noPrinciples != null && !noPrinciples.isEmpty()) {
			setAttr("noPrinciples", StringUtils.array2string(noPrinciples.toArray(new Integer[noPrinciples.size()])));
		}
		setAttr("dictionaryIngredients", Dictionary.dao.find(FIND_FOOD_INGRED));
		render("create.html");
	}

	public void view() {
		int diseaseId = getParaToInt(0);
		List<Principle> principles = Principle.dao.find(
				"select * from principle where type=? and cause_type = ? and cause_id = ?",
				Constant.PRINCIPLE_TYPE.HEALTH.value, Constant.PRINCIPLE_CAUSE_TYPE.DISEASE.value, diseaseId);

		setAttr("principles", Principle.dao.findById(principles));
		setAttr("dictionaryIngredients", Dictionary.dao.find(FIND_FOOD_INGRED));
	}

	public void show() {
		int diseaseId = getParaToInt(0);
		List<Principle> principles = Principle.dao.find(
				"select id from principle where type=? and cause_type = ? and cause_id = ?",
				Constant.PRINCIPLE_TYPE.HEALTH.value, Constant.PRINCIPLE_CAUSE_TYPE.DISEASE.value, diseaseId);
		if (null == principles || principles.isEmpty()) {
			redirect("/principle/create/" + diseaseId);
		} else {
			redirect("/principle/modify/" + diseaseId);
		}
	}

	@Before(Tx.class)
	public void save() {
		int diseaseId = getParaToInt("diseaseid");
		Integer id = getParaToInt("id", null);
		String[] ruleMore = getParaValues("rule_more");
		String[] ruleLess = getParaValues("rule_less");
		String[] ruleNo = getParaValues("rule_no");

		if (null != id) {
			batchDelete(diseaseId);
		}
		batchSave(ruleMore, Constant.PRINCIPLE_ADVERB.MORE, diseaseId);
		batchSave(ruleNo, Constant.PRINCIPLE_ADVERB.NO, diseaseId);
		batchSave(ruleLess, Constant.PRINCIPLE_ADVERB.LESS, diseaseId);

		redirect("/principle/modify/" + diseaseId);
	}

	private void batchDelete(int diseaseId) {
		List<Principle> list = Principle.dao.find(
				"select * from principle where type=? and cause_type=? and cause_id=?", PRINCIPLE_TYPE.HEALTH.value,
				PRINCIPLE_CAUSE_TYPE.DISEASE.value, diseaseId);
		for (Principle principle : list) {
			principle.delete();
		}
	}

	private void batchSave(String[] arr, PRINCIPLE_ADVERB adverb, int causeId) {
		if (null == arr || arr.length == 0) {
			return;
		}

		// TODO Here need batch save method,Do not use for-each
		for (String str : arr) {
			new Principle().set("type", PRINCIPLE_TYPE.HEALTH.value)
					.set("cause_type", PRINCIPLE_CAUSE_TYPE.DISEASE.value).set("cause_id", causeId)
					.set("adverb", adverb.value).set("action", PRINCIPLE_ACTION.EAT.value)
					.set("target_type", PRINCIPLE_TARGET_TYPE.INGREDIENT.value).set("target_id", Integer.valueOf(str))
					.save();
		}

	}

}
