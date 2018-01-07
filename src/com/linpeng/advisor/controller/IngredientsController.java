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
		String name= getPara("name");
		int pageNumber = getParaToInt("page", getParaToInt(0,1));
		int pageSize = getParaToInt("pagesize", 10);

		if(null!=name && name.length()>0){
			setAttr("page",  Ingredient.dao.paginate(pageNumber, pageSize, "select *",
					" from ingredients where name like '%"+name+"%'"));
			setAttr("name",name);
		}else{
			setAttr("page", Ingredient.dao.paginate(pageNumber, pageSize,
					"select *", " from ingredients"));
		}
		
		render("list.html");
	}

	public void detail() {
		int id = getParaToInt("id");
		Ingredient ingredient = Ingredient.dao.findById(id);
		setAttr("ingredient", ingredient);
		render("detail.html");
	}

}
