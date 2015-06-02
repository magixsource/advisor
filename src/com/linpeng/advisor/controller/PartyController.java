package com.linpeng.advisor.controller;

import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.linpeng.advisor.model.Diners;
import com.linpeng.advisor.model.Party;
import com.linpeng.advisor.model.Person;
import com.linpeng.advisor.model.User;

/**
 * Party controller
 * 
 * @author linpeng
 *
 */
public class PartyController extends Controller {

	/**
	 * Create a party
	 */
	public void create() {
		User loginUser = getSessionAttr("loginUser");
		setAttr("userid", loginUser.getInt("id"));
	}

	/**
	 * Update a party
	 */
	public void update() {
		int partyId = getParaToInt(0);

		User loginUser = getSessionAttr("loginUser");
		setAttr("userid", loginUser.getInt("id"));
		setAttr("party", Party.dao.findById(partyId));

		render("create.html");
	}

	@Before(Tx.class)
	public void save() {
		String id = getPara("id", null);
		String userId = getPara("userid");
		String name = getPara("name");

		if (null == id) {
			new Party().set("name", name).set("userid", userId)
					.set("createtime", new Date()).set("status", "1").save();
		} else {
			Party.dao.findById(id).set("name", name).update();
		}

		redirect("/party/list");
	}

	/**
	 * List all party that login user is master
	 */
	public void list() {
		int pageNumber = getParaToInt(0, 1);
		int pageSize = getParaToInt("pagesize", 10);

		User loginUser = getSessionAttr("loginUser");
		int userId = loginUser.getInt("id");

		setAttr("page", Party.dao.paginate(pageNumber, pageSize, "select *",
				" from party where userid = " + userId));

		render("list.html");
	}

	/**
	 * Show diners by party
	 */
	public void diners() {
		int partyId = getParaToInt(0);
		int pageNumber = getParaToInt(1, 1);
		int pageSize = getParaToInt("pagesize", 10);

		// 关联查询
		setAttr("page", Db.paginate(pageNumber, pageSize,
				"select t1.personid,t2.name",
				" from diners t1,person t2 where t1.personid = t2.id and t1.partyid="
						+ partyId));
		setAttr("partyid", partyId);

		render("diners.html");

	}

	/**
	 * Add a diner to party
	 */
	public void manage() {
		String partyid = getPara(0);
		User loginUser = getSessionAttr("loginUser");
		int userId = loginUser.getInt("id");

		setAttr("persons",
				Person.dao.find("select * from person where userid=" + userId));
		setAttr("diners", Diners.dao.find("select * from diners where partyid="
				+ partyid));
		setAttr("partyid", partyid);
	}

	@Before(Tx.class)
	public void saveDiners() {
		String partyId = getPara("partyid");
		String[] personIds = getParaValues("personids");
		if (personIds == null) {
			personIds = new String[] { "" };
		}
		// clear all
		remove(partyId);

		for (String personId : personIds) {
			new Diners().set("partyid", partyId).set("personid", personId)
					.save();
		}

		redirect("/party/diners/" + partyId);

	}

	/**
	 * Remove person from diners
	 * 
	 * @param partyId
	 * @param personId
	 */
	@Before(Tx.class)
	public void remove() {
		String partyId = getPara("partyId");
		String personId = getPara("personId");
		Db.update("delete from diners where partyid=? and personid=?", partyId,
				personId);
		redirect("/party/diners/" + partyId);
	}

	/**
	 * Remove a diner to party
	 */
	private void remove(String partyId) {
		Db.update("delete from diners where partyid=?", partyId);
	}

}
