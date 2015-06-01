package com.linpeng.advisor.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 
 * @author linpeng
 *
 */
public class PersonController extends Controller {

	/**
	 * Create a person information
	 */
	public void create() {

	}

	public void update() {

	}

	public void list() {

	}

	@Before(Tx.class)
	public void save() {

	}

}
