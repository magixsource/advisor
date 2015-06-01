package com.linpeng.advisor.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Ingredient model
 * 
 * @author linpeng
 *
 */
public class Ingredient extends Model<Ingredient> {

	private static final long serialVersionUID = -1427977150183147365L;

	public static final Ingredient dao = new Ingredient();

	/**
	 * Milligram unit fields(1000 milligram = 1gram )
	 */
	public static final String[] INGREDIENT_MILLIGRAM_FIELDS = new String[] {
			"retinol_equivalent", "vb1", "vb2", "vpp", "vitamin_a",
			"vitamin_b1", "vitamin_b2", "vitamin_b6", "vitamin_b12",
			"vitamin_c", "vitamin_d", "vitamin_e", "vitamin_k", "natrium",
			"calcium", "ferrum", "vc", "cholestenone" };
	/**
	 * Gram unit fields
	 */
	public static final String[] INGREDIENT_GRAM_FIELDS = new String[] {
			"water", "protein", "fat", "dietary_fiber", "carbohydrate" };

	/**
	 * Calorie unit fields
	 */
	public static final String[] INGREDIENT_CALORIE_FIELDS = new String[] { "energy" };
}
