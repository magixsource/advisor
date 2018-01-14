package com.linpeng.advisor.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.linpeng.advisor.annotation.AopIgnore;
import com.linpeng.advisor.common.AdvisorUtils;
import com.linpeng.advisor.common.Constant.PRINCIPLE_ADVERB;
import com.linpeng.advisor.common.StringUtils;
import com.linpeng.advisor.interceptor.AuthInterceptor;
import com.linpeng.advisor.model.Dictionary;
import com.linpeng.advisor.model.Disease;
import com.linpeng.advisor.model.Ingredient;
import com.linpeng.advisor.model.KV;
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
			List<Disease> diseases = findDiseaseByName(diseaseName);
			if (null == diseases || diseases.isEmpty()) {
				setAttr("errorMsg", diseaseName + NOT_FOUND_SUFFIX);
			} else {
				List<Principle> principles = findPrinciplesByDiseases(diseases);
				if (principles == null || principles.isEmpty()) {
					setAttr("errorMsg", diseaseName + NOT_FOUND_SUFFIX);
				}
				// result
				Page<Ingredient> page = paginateIngredient(pageNumber, pageSize, diseases, principles);

				// String tips = generateTipsByPrinciple(principles, true);
				Map<String, Object> tipsMap = generateTipsMapByPrinciple(principles);
				setAttr("page", page);
				setAttr("q", diseaseName);
				setAttr("tips", tipsMap);
			}

		}
		render("index.html");
	}

	public void adviseJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		String diseaseName = getPara("q");
		if (null == diseaseName || diseaseName.trim().length() == 0) {
			map.put("errormsg", MSG_INPUT_DISEASE_NAME);
		} else {
			List<Disease> diseases = findDiseaseByName(diseaseName);
			if (null == diseases || diseases.isEmpty()) {
				map.put("errormsg", diseaseName + NOT_FOUND_SUFFIX);
			} else {
				List<Principle> principles = findPrinciplesByDiseases(diseases);
				if (principles == null || principles.isEmpty()) {
					map.put("errormsg", diseaseName + NOT_FOUND_SUFFIX);
				}

				String tips = generateTipsByPrinciple(principles, false);
				List<String> tipList = Arrays.asList(tips.split(";"));
				List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < tipList.size(); i++) {
					String[] keys = { "more", "less", "no" };
					Map<String, Object> innerMap = new HashMap<String, Object>();
					innerMap.put(keys[i], tipList.get(i).split(","));
					result.add(innerMap);
				}

				// 取前五的数据
				Page<Ingredient> moreFood = paginateIngredient(PRINCIPLE_ADVERB.MORE, 1, 5, diseases, principles);
				Page<Ingredient> lessFood = paginateIngredient(PRINCIPLE_ADVERB.LESS, 1, 5, diseases, principles);
				Page<Ingredient> noFood = paginateIngredient(PRINCIPLE_ADVERB.NO, 1, 5, diseases, principles);

				Map<String, List> foodMap = new HashMap<String, List>();
				foodMap.put("more", zip(moreFood.getList()));
				foodMap.put("less", zip(lessFood.getList()));
				foodMap.put("no", zip(noFood.getList()));

				map.put("q", diseaseName);
				map.put("foods", foodMap);
				map.put("tips", result);
			}
		}
		renderJson(map);
	}

	private List zip(List<Ingredient> list) {
		List<KV> kvs = new ArrayList<KV>(list.size());
		for (Ingredient ingredient : list) {
			kvs.add(new KV(ingredient.getInt("id").toString(), ingredient.getStr("name")));
		}
		return kvs;
	}

	/**
	 * 构造贴士映射表
	 * 
	 * @param principles
	 * @return
	 */
	private Map<String, Object> generateTipsMapByPrinciple(List<Principle> principles) {
		if (null == principles) {
			return null;
		}
		String rule_more = principleRuleAsString(principles, "rule_more", ",");
		String rule_less = principleRuleAsString(principles, "rule_less", ",");
		String rule_no = principleRuleAsString(principles, "rule_no", ",");

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

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("more", moreTip);
		result.put("less", lessTip);
		result.put("no", noTip);
		return result;
	}

	/**
	 * Generate tips
	 * 
	 * @param principle
	 * @param isTranslate
	 * @return
	 */
	private String generateTipsByPrinciple(List<Principle> principles, boolean isTranslate) {
		if (null == principles) {
			return "找不到相关记录 :(";
		}

		String more = "多吃-%s";
		String less = "少吃-%s";
		String no = "不吃-%s";

		Map<String, Object> map = generateTipsMapByPrinciple(principles);
		String moreTip = map.get("more").toString();
		String lessTip = map.get("less").toString();
		String noTip = map.get("no").toString();
		if (isTranslate) {
			return "小贴士：建议" + String.format(more, moreTip) + " " + String.format(less, lessTip) + " "
					+ String.format(no, noTip);
		} else {
			return moreTip + ";" + lessTip + ";" + noTip;
		}

	}

	private String principleRuleAsString(List<Principle> principles, String fieldName, String delimiter) {
		StringBuffer ruleStr = new StringBuffer(100);
		for (Principle principle : principles) {
			ruleStr.append(principle.getStr(fieldName));
			ruleStr.append(delimiter);
		}
		return ruleStr.toString();
	}

	private List<Disease> findDiseaseByName(String diseaseName) {
		return Disease.dao.find("select * from disease where name in('" + diseaseName.replaceAll(";", "','") + "')");
	}

	private List<Principle> findPrinciplesByDiseases(List<Disease> diseases) {
		String[] ids = new String[diseases.size()];
		for (int i = 0; i < diseases.size(); i++) {
			ids[i] = diseases.get(i).getInt("id").toString();
		}
		String sql = "select * from principle where disease_id in('" + StringUtils.array2string(ids, "','") + "')";
		return Principle.dao.find(sql);
	}

	private Page<Ingredient> paginateIngredient(int pageNumber, int pageSize, List<Disease> diseases,
			List<Principle> principles) {
		return Ingredient.dao.paginate(pageNumber, pageSize, "select t.*",
				" from ingredients t where " + conditionBuilder(null, principles));
	}

	private Page<Ingredient> paginateIngredient(PRINCIPLE_ADVERB adverb, int pageNumber, int pageSize,
			List<Disease> diseases, List<Principle> principles) {

		return Ingredient.dao.paginate(pageNumber, pageSize, "select t.*",
				" from ingredients t where " + conditionBuilder(adverb, principles));
	}

	/**
	 * 按规则构造查询多、少、禁的SQL语句
	 * 
	 * @param rule
	 *            规则标志
	 * @param principles
	 *            规则集合
	 * @return 查询多，少，禁的SQL语句
	 */
	private String conditionBuilder(PRINCIPLE_ADVERB adverb, List<Principle> principles) {

		if (principles == null || principles.isEmpty()) {
			return "1=2";
		}
		String delimiter = ",";
		String ruleMore = null;
		String ruleLess = null;
		String ruleNo = null;

		if (null == adverb) {
			ruleMore = principleRuleAsString(principles, "rule_more", delimiter);
			ruleLess = principleRuleAsString(principles, "rule_less", delimiter);
			ruleNo = principleRuleAsString(principles, "rule_no", delimiter);
		} else {
			if (adverb.value.equals(PRINCIPLE_ADVERB.MORE.value)) {// 单独取多的
				ruleMore = principleRuleAsString(principles, "rule_more", delimiter);
			} else if (adverb.value.equals(PRINCIPLE_ADVERB.LESS.value)) {// 单独取少的
				ruleLess = principleRuleAsString(principles, "rule_less", delimiter);
			} else if (adverb.value.equals(PRINCIPLE_ADVERB.NO.value)) {// 单独取禁的
				ruleNo = principleRuleAsString(principles, "rule_no", delimiter);
			}
		}
		return AdvisorUtils.sqlConditionGennerate(ruleMore, ruleLess, ruleNo);
	}

}
