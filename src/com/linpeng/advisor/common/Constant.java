package com.linpeng.advisor.common;

/**
 * Advisor constant class
 * 
 * @author linpeng
 *
 */
public class Constant {
	
	public enum PRINCIPLE_ACTION{
		EAT(1,"吃");
		
		public String title;
		public Integer value;

		PRINCIPLE_ACTION(Integer value, String title) {
			this.value = value;
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}

	public enum PRINCIPLE_ADVERB {
		MORE(1, "多"), LESS(2, "少"), NO(3, "禁");
		public String title;
		public Integer value;

		PRINCIPLE_ADVERB(Integer value, String title) {
			this.value = value;
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}

	public enum PRINCIPLE_CAUSE_TYPE {
		DISEASE(1, "疾病");

		public String title;
		public Integer value;

		PRINCIPLE_CAUSE_TYPE(Integer value, String title) {
			this.value = value;
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}

	public enum PRINCIPLE_TARGET_TYPE {
		INGREDIENT(1, "营养成分");

		public String title;
		public Integer value;

		PRINCIPLE_TARGET_TYPE(Integer value, String title) {
			this.value = value;
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

	}

	public enum PRINCIPLE_TYPE {
		HEALTH(1, "健康饮食推荐");
		public String title;
		public Integer value;

		PRINCIPLE_TYPE(Integer value, String title) {
			this.value = value;
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

	}

}
