package com.linpeng.advisor.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * User model, very simple model
 * 
 * @author linpeng
 *
 */
public class User extends Model<User> {
	private static final long serialVersionUID = -8365053160437371066L;
	public final static User dao = new User();
}
