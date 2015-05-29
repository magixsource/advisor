package com.linpeng.advisor.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.linpeng.advisor.annotation.AopIgnore;
import com.linpeng.advisor.common.StringUtils;
import com.linpeng.advisor.config.BaseConfig;
import com.linpeng.advisor.interceptor.AuthInterceptor;
import com.linpeng.advisor.model.Dictionary;
import com.linpeng.advisor.model.Disease;
import com.linpeng.advisor.model.Ingredient;
import com.linpeng.advisor.model.Principle;

/**
 * Application index controller
 * 
 * @author linpeng
 *
 */
// FIXME Test-case when rule_more or rule_less or rule_no is empty (#Fixed)
// FIXME The different value should not hard-code in method getLessGramByField
// (#Fixed)
// FIXME More smart and good algorithm should conditionBuilder method be
// FIXME Query result order problem(#Fixed)
@AopIgnore(AuthInterceptor.class)
public class IndexController extends Controller {

	public static final String NOT_FOUND_SUFFIX = " Not Found !";
	public static final String MSG_INPUT_DISEASE_NAME = "Input disease name plz !";

	public void index() {
	}

	/**
	 * advise according disease name
	 */
	public void advise() {
		String diseaseName = getPara("q");
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pagesize", 10);
		if (null == diseaseName || diseaseName.trim().length() == 0) {
			setAttr("errorMsg", MSG_INPUT_DISEASE_NAME);
		} else {
			Disease disease = findDiseaseByName(diseaseName);
			if (null == disease) {
				setAttr("errorMsg", diseaseName + NOT_FOUND_SUFFIX);
			} else {
				Principle principle = findPrincipleByDiseaseId(disease);
				// result
				Page<Ingredient> page = paginateIngredient(pageNumber,
						pageSize, disease, principle);
				String tips = generateTipsByPrinciple(principle);
				setAttr("page", page);
				setAttr("q", diseaseName);
				setAttr("tips", tips);
			}

		}
		render("index.html");
	}

	/**
	 * Generate tips
	 * 
	 * @param principle
	 * @return
	 */
	private String generateTipsByPrinciple(Principle principle) {

		String rule_more = principle.getStr("rule_more");
		String rule_less = principle.getStr("rule_less");
		String rule_no = principle.getStr("rule_no");
		String more = "多吃-%s";
		String less = "少吃-%s";
		String no = "不吃-%s";

		List<Dictionary> list = Dictionary.dao
				.find("select t.code,t.title from dictionary t where t.kind='APP_DICT_INGRED'");

		Map<String, String> map = new HashMap<String, String>();
		for (Dictionary dic : list) {
			if (dic == null) {
				continue;
			}
			map.put(dic.getStr("code"), dic.getStr("title"));
		}

		String moreTip = rule_more;
		for (String s : rule_more.split(",")) {
			moreTip = moreTip.replace(s, map.get(s));
		}
		String lessTip = rule_less;
		for (String s : rule_less.split(",")) {
			lessTip = lessTip.replace(s, map.get(s));
		}
		String noTip = rule_no;
		for (String s : rule_no.split(",")) {
			noTip = noTip.replace(s, map.get(s));
		}

		return "小贴士：建议"+String.format(more, moreTip) + " "
				+ String.format(less, lessTip) + " " + String.format(no, noTip);
	}

	private Disease findDiseaseByName(String diseaseName) {
		return Disease.dao.findFirst("select * from disease where name=?",
				diseaseName);
	}

	public void adviseJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		String diseaseName = getPara("q");
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pagesize", 10);
		if (null == diseaseName || diseaseName.trim().length() == 0) {
			map.put("errormsg", MSG_INPUT_DISEASE_NAME);
		} else {
			Disease disease = findDiseaseByName(diseaseName);
			if (null == disease) {
				map.put("errormsg", diseaseName + NOT_FOUND_SUFFIX);
			} else {
				Principle principle = findPrincipleByDiseaseId(disease);
				// result
				Page<Ingredient> page = paginateIngredient(pageNumber,
						pageSize, disease, principle);
				map.put("page", page);
				map.put("q", diseaseName);
			}
		}
		renderJson(map);
	}

	private Principle findPrincipleByDiseaseId(Disease disease) {
		return Principle.dao.findFirst(
				"select * from Principle where disease_id = ?",
				disease.getInt("id"));
	}

	private Page<Ingredient> paginateIngredient(int pageNumber, int pageSize,
			Disease disease, Principle principle) {
		return Ingredient.dao.paginate(
				pageNumber,
				pageSize,
				"select t.*",
				" from ingredients t where "
						+ conditionBuilder(disease, principle));
	}

	/**
	 * Build sql query condition by disease(CORE)
	 * 
	 * @param disease
	 * @return
	 */
	protected String conditionBuilder(Disease disease, Principle principle) {
		return sqlConditionGennerate(principle.getStr("rule_more"),
				principle.getStr("rule_less"), principle.getStr("rule_no"));
	}

	/**
	 * SQL-express generate according more\less\no principle
	 * 
	 * @param strMore
	 * @param strLess
	 * @param strNo
	 * @return
	 */
	protected String sqlConditionGennerate(String strMore, String strLess,
			String strNo) {
		StringBuffer sb = new StringBuffer(100);

		String[] ruleMoreArray = null != strMore ? StringUtils
				.string2array(strMore) : null;
		String[] ruleLessArray = null != strLess ? StringUtils
				.string2array(strLess) : null;
		String[] ruleNoArray = null != strNo ? StringUtils.string2array(strNo)
				: null;

		if (null != ruleMoreArray && null != ruleLessArray
				&& null != ruleNoArray) {
			// More or Less begin
			sb.append("(");
		}

		if (null != ruleMoreArray) {
			sb.append("(");
			for (int i = 0; i < ruleMoreArray.length; i++) {
				if (i > 0) {
					sb.append(" OR ");
				}
				sb.append(ruleMoreArray[i]);
				sb.append(">0");
			}
			sb.append(")");
		}

		if (null != ruleLessArray) {
			// 'More' and 'Less' relation
			if (null != ruleMoreArray) {
				sb.append(" AND ");
			}

			sb.append("(");
			for (int i = 0; i < ruleLessArray.length; i++) {
				if (i > 0) {
					sb.append(" AND ");
				}
				sb.append(ruleLessArray[i]);
				sb.append("<=" + getLessValueByField(ruleLessArray[i]));
			}
			sb.append(")");
		}

		if (null != ruleMoreArray && null != ruleLessArray
				&& null != ruleNoArray) {
			// More or Less end
			sb.append(")");
		}

		if (null != ruleNoArray) {
			// 'More-Less' and 'No' relation
			if (null != ruleMoreArray || null != ruleLessArray) {
				sb.append(" AND ");
			}

			sb.append("(");
			for (int i = 0; i < ruleNoArray.length; i++) {
				if (i > 0) {
					sb.append(" AND ");
				}
				sb.append(ruleNoArray[i]);
				sb.append("<=" + getNoValueByField(ruleNoArray[i]));
			}
			sb.append(")");
		}

		// order append
		if (null != ruleMoreArray || null != ruleLessArray) {
			sb.append(" order by ");
		}
		if (null != ruleMoreArray) {
			for (int i = 0; i < ruleMoreArray.length; i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(ruleMoreArray[i]);
			}
			sb.append(" DESC");
		}
		if (null != ruleLessArray) {
			// 琛�,'鍙�
			if (null != ruleMoreArray) {
				sb.append(",");
			}
			for (int i = 0; i < ruleLessArray.length; i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(ruleLessArray[i]);
			}
			sb.append(" ASC");
		}

		return sb.toString();
	}

	/**
	 * How much that is no
	 * 
	 * @param string
	 * @return
	 */
	private String getNoValueByField(String fieldName) {
		if (Arrays.asList(Ingredient.INGREDIENT_CALORIE_FIELDS).contains(
				fieldName)) {
			return BaseConfig.appProperties.get("advise.calorie.nolt")
					.toString();
		} else if (Arrays.asList(Ingredient.INGREDIENT_GRAM_FIELDS).contains(
				fieldName)) {
			return BaseConfig.appProperties.get("advise.gram.nolt").toString();
		} else if (Arrays.asList(Ingredient.INGREDIENT_MILLIGRAM_FIELDS)
				.contains(fieldName)) {
			return BaseConfig.appProperties.get("advise.milligram.nolt")
					.toString();
		}
		throw new IllegalArgumentException(fieldName + " undefined !");
	}

	/**
	 * How much that is less
	 * 
	 * @param string
	 * @return
	 */
	private String getLessValueByField(String fieldName) {
		if (Arrays.asList(Ingredient.INGREDIENT_CALORIE_FIELDS).contains(
				fieldName)) {
			return BaseConfig.appProperties.get("advise.calorie.lt").toString();
		} else if (Arrays.asList(Ingredient.INGREDIENT_GRAM_FIELDS).contains(
				fieldName)) {
			return BaseConfig.appProperties.get("advise.gram.lt").toString();
		} else if (Arrays.asList(Ingredient.INGREDIENT_MILLIGRAM_FIELDS)
				.contains(fieldName)) {
			return BaseConfig.appProperties.get("advise.milligram.lt")
					.toString();
		}
		throw new IllegalArgumentException(fieldName + " undefined !");
	}
}
