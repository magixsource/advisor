package com.linpeng.advisor.controller;

import com.jfinal.core.Controller;
import com.linpeng.advisor.model.Ingredient;

/**
 * Ingredient controller
 * 
 * @author linpeng
 *
 */
public class IngredientsController extends Controller {

	/**
	 * pagination ingredients
	 */
	public void list() {
		int pageNumber = getParaToInt(0, 1);
		int pageSize = getParaToInt("pagesize", 10);

		setAttr("page", Ingredient.dao.paginate(pageNumber, pageSize,
				"select *", " from Ingredients"));

		render("list.html");
	}

	public void detail() {
		int id = getParaToInt("sid");
		Ingredient ingredient = Ingredient.dao.findById(id);
		setAttr("ingredient", ingredient);
		render("detail.html");
	}

}
