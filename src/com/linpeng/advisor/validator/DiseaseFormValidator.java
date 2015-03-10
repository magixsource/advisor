package com.linpeng.advisor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.linpeng.advisor.controller.DiseaseController;
import com.linpeng.advisor.model.Dictionary;

/**
 * Disease Validator
 * 
 * @author linpeng
 *
 */
public class DiseaseFormValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("name", "nameMsg", "please enter name");
		validateRequiredString("dept", "deptMsg", "please choise dept");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("sid");
		c.setAttr("dictionaryList",
				Dictionary.dao.find(DiseaseController.FIND_DISEASE_DEPT));
		c.setAttr("dictionaryIngredients",
				Dictionary.dao.find(DiseaseController.FIND_FOOD_INGRED));
		c.render("create.html");
	}

}
