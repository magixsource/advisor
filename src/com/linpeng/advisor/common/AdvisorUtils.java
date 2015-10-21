package com.linpeng.advisor.common;

import java.util.Arrays;
import java.util.List;

import com.linpeng.advisor.config.BaseConfig;
import com.linpeng.advisor.model.Ingredient;
import com.linpeng.advisor.model.Principle;

/**
 * Advisor query functions
 * 
 * @author linpeng
 *
 */
public class AdvisorUtils {

	/**
	 * SQL-express generate according more\less\no principle
	 * 
	 * @param strMore
	 * @param strLess
	 * @param strNo
	 * @return
	 */
	public static String sqlConditionGennerate(String strMore, String strLess,
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
	public static String getNoValueByField(String fieldName) {
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
	public static String getLessValueByField(String fieldName) {
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

	public static String sqlConditionGennerate(List<Principle> principles) {
		// List<String> moreList = new ArrayList<String>();
		// List<String> lessList = new ArrayList<String>();
		// List<String> noList = new ArrayList<String>();
		String moreStr = "";
		for (Principle principle : principles) {
			String ruleMore = principle.getStr("rule_more");
			String ruleLess = principle.getStr("rule_less");
			String ruleNo = principle.getStr("rule_no");
		}
		return null;
	}
}
