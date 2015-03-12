package com.linpeng.advisor.controller;

import java.util.Arrays;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.linpeng.advisor.annotation.AopIgnore;
import com.linpeng.advisor.common.StringUtils;
import com.linpeng.advisor.interceptor.AuthInterceptor;
import com.linpeng.advisor.model.Disease;
import com.linpeng.advisor.model.Ingredient;
import com.linpeng.advisor.model.Principle;

/**
 * Application index controller
 * 
 * @author linpeng
 *
 */
// FIXME Test-case when rule_more or rule_less or rule_no is empty
// FIXME The different value should not hard-code in method getLessGramByField
// FIXME More smart and good algorithm should conditionBuilder method be
// FIXME Query result order problem
@AopIgnore(AuthInterceptor.class)
public class IndexController extends Controller {

	public void index() {

	}

	/**
	 * advise according disease name
	 */
	public void advise() {
		String diseaseName = getPara("q");
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pagesize", 10);

		Disease disease = Disease.dao.findFirst(
				"select * from disease where name=?", diseaseName);
		if (null == disease) {
			setAttr("errorMsg", diseaseName + " Not Found !");
		} else {
			// result
			Page<Ingredient> page = Ingredient.dao.paginate(pageNumber,
					pageSize, "select t.*", " from ingredients t where "
							+ conditionBuilder(disease));
			setAttr("page", page);
			setAttr("q", diseaseName);
		}
		render("index.html");
	}

	/**
	 * Build sql query condition by disease(CORE)
	 * 
	 * @param disease
	 * @return
	 */
	private String conditionBuilder(Disease disease) {
		StringBuffer sb = new StringBuffer(100);
		Principle principle = Principle.dao.findFirst(
				"select * from Principle where disease_id = ?",
				disease.getInt("id"));
		String[] roleMoreArray = StringUtils.string2array(principle
				.getStr("rule_more"));
		String[] roleLessArray = StringUtils.string2array(principle
				.getStr("rule_less"));
		String[] roleNoArray = StringUtils.string2array(principle
				.getStr("rule_no"));

		// More or Less begin
		sb.append("(");
		sb.append("(");
		for (int i = 0; i < roleMoreArray.length; i++) {
			if (i > 0) {
				sb.append("OR ");
			}
			sb.append(roleMoreArray[i]);
			sb.append(">0 ");
		}
		sb.append(")");

		// 'More' and 'Less' relation
		sb.append(" OR ");

		sb.append("(");
		for (int i = 0; i < roleLessArray.length; i++) {
			if (i > 0) {
				sb.append("AND ");
			}
			sb.append(roleLessArray[i]);
			sb.append("<=" + getLessGramByField(roleLessArray[i]) + " ");
		}
		sb.append(")");

		// More or Less end
		sb.append(")");

		// 'More-Less' and 'No' relation
		sb.append(" AND ");

		sb.append("(");
		for (int i = 0; i < roleNoArray.length; i++) {
			if (i > 0) {
				sb.append("AND ");
			}
			sb.append(roleNoArray[i]);
			sb.append("= 0 ");
		}
		sb.append(")");

		return sb.toString();
	}

	/**
	 * How much that is less
	 * 
	 * @param string
	 * @return
	 */
	private String getLessGramByField(String fieldName) {
		if (Arrays.asList(Ingredient.INGREDIENT_CALORIE_FIELDS).contains(
				fieldName)) {
			return "200";
		} else if (Arrays.asList(Ingredient.INGREDIENT_GRAM_FIELDS).contains(
				fieldName)) {
			return "5";
		} else if (Arrays.asList(Ingredient.INGREDIENT_MILLIGRAM_FIELDS)
				.contains(fieldName)) {
			return "1.5";
		}
		throw new IllegalArgumentException(fieldName + " undefined !");
	}
}
