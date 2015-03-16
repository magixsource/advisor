package com.linpeng.advisor.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.linpeng.advisor.model.Dictionary;
import com.linpeng.advisor.model.DictionaryKind;
import com.linpeng.advisor.validator.DictionaryFormValidator;
import com.linpeng.advisor.validator.DictionaryKindFormValidator;

/**
 * Dictionary controller
 * 
 * @author linpeng
 *
 */
public class DictionaryController extends Controller {

	/**
	 * Create a dictionary kind
	 */
	public void create() {
	}

	/**
	 * Modify dictionary kind
	 */
	public void modify() {
		int id = getParaToInt(0);
		DictionaryKind dictionaryKind = DictionaryKind.dao.findById(id);
		setAttr("dictionarykind", dictionaryKind);
		render("create.html");
	}

	/**
	 * Save dictionary
	 */
	@Before(DictionaryKindFormValidator.class)
	public void save() {
		String id = getPara("id", null);
		String title = getPara("title");
		String kind = getPara("kind");
		String parentIdStr = getPara("parentid", null);
		Integer parentId = null;
		if (null != parentIdStr) {
			parentId = Integer.valueOf(parentIdStr);
		}

		if (null == id) {
			new DictionaryKind().set("title", title).set("kind", kind)
					.set("parentid", parentId).save();
		} else {
			DictionaryKind.dao.findById(id).set("title", title)
					.set("kind", kind).set("parentid", parentId).update();
		}

		redirect("/dictionary/list");
	}

	/**
	 * View dictionary kind and dictionary
	 */
	public void view() {
		int id = getParaToInt(0);
		DictionaryKind dictionaryKind = DictionaryKind.dao.findById(id);
		setAttr("dictionarykind", dictionaryKind);
	}

	/**
	 * Delete dictionary kind and dictionary
	 */
	@Before(Tx.class)
	public void delete() {
		int id = getParaToInt(0);
		// step 1, delete all dictionary item
		List<Dictionary> list = Dictionary.dao.find(
				"select * from dictionary where kind=?", id);
		for (Dictionary dictionary : list) {
			dictionary.delete();
		}
		// step2, delete self
		DictionaryKind.dao.deleteById(id);

		redirect("/dictionary/list");
	}

	/**
	 * List dictionary kind
	 */
	public void list() {
		int pageNumber = getParaToInt(0, 1);
		int pageSize = getParaToInt("pagesize", 10);

		setAttr("page", DictionaryKind.dao.paginate(pageNumber, pageSize,
				"select *", " from dictionary_kind"));

		render("list.html");
	}

	// --------------------------------------------

	/**
	 * Create a dictionary item of kind
	 */
	public void add() {
		int kindId = getParaToInt(0);
		DictionaryKind dictionaryKind = DictionaryKind.dao.findById(kindId);
		setAttr("dictionarykind", dictionaryKind);
		render("add.html");
	}

	@Before(DictionaryFormValidator.class)
	public void saveDict() {
		int kindId = getParaToInt("kindid");
		String id = getPara("id", null);
		String title = getPara("title");
		String code = getPara("code");

		if (null == id) {

			new Dictionary()
					.set("title", title)
					.set("kind",
							DictionaryKind.dao.findById(kindId).get("kind"))
					.set("code", code).save();
		} else {
			Dictionary.dao.findById(id).set("title", title).set("code", code)
					.update();
		}

		redirect("/dictionary/all/" + kindId);
	}

	/**
	 * Delete a dictionary item of kind
	 */
	public void remove() {
		// delete item
		int id = getParaToInt(0);
		Dictionary dict = Dictionary.dao.findById(id);
		int kindId = dict.get("kindid");
		dict.delete();

		redirect("/dictionary/all/" + kindId);
	}

	/**
	 * List all dictionary item of kind
	 */
	public void all() {
		int kindId = getParaToInt(0);
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pagesize", 10);

		setAttr("kindid", kindId);
		setAttr("page",
				Dictionary.dao
						.paginate(
								pageNumber,
								pageSize,
								"select t.*",
								" from dictionary t,dictionary_kind k where t.kind=k.kind and k.id=?",
								kindId));

		render("all.html");
	}
}
