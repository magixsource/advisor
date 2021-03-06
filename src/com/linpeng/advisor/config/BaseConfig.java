package com.linpeng.advisor.config;

import java.util.Properties;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.linpeng.advisor.controller.AdvisorController;
import com.linpeng.advisor.controller.DictionaryController;
import com.linpeng.advisor.controller.DiseaseController;
import com.linpeng.advisor.controller.IndexController;
import com.linpeng.advisor.controller.IngredientsController;
import com.linpeng.advisor.controller.LoginController;
import com.linpeng.advisor.controller.MenuController;
import com.linpeng.advisor.controller.PartyController;
import com.linpeng.advisor.controller.PersonController;
import com.linpeng.advisor.controller.PrincipleController;
import com.linpeng.advisor.controller.SignController;
import com.linpeng.advisor.controller.UserController;
import com.linpeng.advisor.interceptor.AuthInterceptor;
import com.linpeng.advisor.interceptor.GlobalInterceptor;
import com.linpeng.advisor.interceptor.MenuInjectIntercepter;
import com.linpeng.advisor.model.Dictionary;
import com.linpeng.advisor.model.DictionaryKind;
import com.linpeng.advisor.model.Diners;
import com.linpeng.advisor.model.Disease;
import com.linpeng.advisor.model.Ingredient;
import com.linpeng.advisor.model.Menu;
import com.linpeng.advisor.model.Party;
import com.linpeng.advisor.model.Person;
import com.linpeng.advisor.model.Principle;
import com.linpeng.advisor.model.User;

/**
 * application global configuration class
 * 
 * @author linpeng
 *
 */
public class BaseConfig extends JFinalConfig {

	public static Properties appProperties;

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setBaseViewPath("/views");
		me.setViewType(ViewType.FREE_MARKER);
	}

	@Override
	public void configRoute(Routes me) {
		// Common-Manage module
		me.add("/", IndexController.class);
		me.add("/login", LoginController.class, "login");
		me.add("/signup", SignController.class, "signup");
		me.add("/menu", MenuController.class, "menu");

		// Powerful advisor
		me.add("/advisor", AdvisorController.class, "advisor");

		// User Module
		me.add("/user", UserController.class, "user");
		me.add("/person", PersonController.class, "person");
		me.add("/party", PartyController.class, "party");
		// Food Module
		me.add("/ingredient", IngredientsController.class, "ingredient");

		// Disease Module
		me.add("/disease", DiseaseController.class, "disease");

		// Common-Dictionary module
		me.add("/dictionary", DictionaryController.class, "dictionary");

		// Advice module
		me.add("/principle", PrincipleController.class, "principle");
	}

	@Override
	public void configPlugin(Plugins me) {
		Properties properties = loadPropertyFile("db.properties");
		C3p0Plugin cp = new C3p0Plugin(properties);
		me.add(cp);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());

		arp.addMapping("user", User.class);
		arp.addMapping("ingredients", Ingredient.class);
		arp.addMapping("disease", Disease.class);
		arp.addMapping("dictionary_kind", DictionaryKind.class);
		arp.addMapping("dictionary", Dictionary.class);
		arp.addMapping("menu", Menu.class);
		arp.addMapping("principle", Principle.class);
		arp.addMapping("person", Person.class);
		arp.addMapping("party", Party.class);
		arp.addMapping("diners", Diners.class);

		// load app properties
		if (null == appProperties) {
			appProperties = loadPropertyFile("app.properties");
		}
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
		me.add(new AuthInterceptor());
		me.add(new MenuInjectIntercepter());
		me.add(new GlobalInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
	}
}
