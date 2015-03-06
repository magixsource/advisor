package com.linpeng.advisor.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
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
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pagesize", 10);

		Page<Ingredient> page = Ingredient.dao.paginate(pageNumber, pageSize,
				"select *", " from Ingredients");

		setAttr("ingredients", page.getList());
		setAttr("page", page.getPageNumber());
		setAttr("pagesize", page.getPageSize());
		setAttr("total", page.getTotalRow());
		setAttr("totalpage", page.getTotalPage());

		render("list.html");
	}

	public void detail() {
		int id = getParaToInt("sid");
		Ingredient ingredient = Ingredient.dao.findById(id);
		setAttr("ingredient", ingredient);
		render("detail.html");
	}

}
