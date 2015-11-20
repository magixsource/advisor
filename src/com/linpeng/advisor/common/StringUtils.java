package com.linpeng.advisor.common;

/**
 * String & Array tools
 * 
 * @author linpeng
 *
 */
public class StringUtils {
	/** Default Delimiter String */
	public static final String DEFAULT_DELIMITER_STRING = ",";

	/**
	 * Java array to String by DEFAULT_DELIMITER_STRING
	 * 
	 * @param array
	 *            source array
	 * @return String
	 */
	public static String array2string(String[] array) {
		return array2string(array, DEFAULT_DELIMITER_STRING);
	}

	/**
	 * According delimiter parse array to string
	 * 
	 * @param array
	 *            source array
	 * @param delimiter
	 *            according by
	 * @return string
	 */
	public static String array2string(String[] array, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (String str : array) {
			sb.append(str);
			sb.append(delimiter);
		}
		return sb.substring(0, sb.length() - delimiter.length());
	}

	/**
	 * String parse to Java Array
	 * 
	 * @param str
	 *            a string should split by DEFAULT_DELIMITER_STRING
	 * @return array
	 */
	public static String[] string2array(String str) {
		return string2array(str, DEFAULT_DELIMITER_STRING);
	}

	/**
	 * According delimiter parse String to Java Array
	 * 
	 * @param str
	 *            a string should split by delimiter
	 * @param delimiter
	 *            string should split by
	 * @return array
	 */
	public static String[] string2array(String str, String delimiter) {
		return str.split(delimiter);
	}

}
