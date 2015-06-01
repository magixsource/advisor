package com.linpeng.advisor.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Person model
 * 
 * @author linpeng
 *
 */
public class Person extends Model<Person> {

	private static final long serialVersionUID = -3285728980109886653L;

	public static final Person dao = new Person();

}
