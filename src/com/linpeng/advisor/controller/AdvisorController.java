package com.linpeng.advisor.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.linpeng.advisor.common.AdvisorUtils;
import com.linpeng.advisor.common.StringUtils;
import com.linpeng.advisor.model.Ingredient;
import com.linpeng.advisor.model.Principle;

/**
 * Make a advise by request
 * 
 * @author linpeng
 *
 */
public class AdvisorController extends Controller {

	/**
	 * Give a advise by disease name(support single user or a eater list)
	 */
	public void diseaseAdvisor() {
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pagesize", 10);

		String[] diseases = getParaValues("diseases");
		List<Principle> principles = findPrincipleByDiseases(diseases);
		Page<Ingredient> page = pagination(pageNumber, pageSize, diseases,
				principles);
		String tips = generateTips(principles);

		setAttr("page", page);
		setAttr("tips", tips);

	}

	private String generateTips(List<Principle> principles) {
		return null;
	}

	private Page<Ingredient> pagination(int pageNumber, int pageSize,
			String[] diseases, List<Principle> principles) {
		return Ingredient.dao.paginate(
				pageNumber,
				pageSize,
				"select t.*",
				" from ingredients t where "
						+ conditionBuilder(diseases, principles));
	}

	private String conditionBuilder(String[] diseases,
			List<Principle> principles) {
		return AdvisorUtils.sqlConditionGennerate(principles);
	}

	private List<Principle> findPrincipleByDiseases(String[] diseases) {
		String sql = "select * from Principle where disease_id in(?)";
		return Principle.dao.find(sql,
				"'" + StringUtils.array2string(diseases, "','") + "'");
	}

	/**
	 * Give a advise by ingredient(check ingredient is fit or not)[support
	 * single user or a eater list]
	 */
	public void ingredientAdvisor() {
	}

	// create a eater list

}
