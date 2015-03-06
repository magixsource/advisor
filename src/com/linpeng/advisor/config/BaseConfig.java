package com.linpeng.advisor.config;

import java.util.Properties;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.linpeng.advisor.controller.IngredientsController;
import com.linpeng.advisor.controller.LoginController;
import com.linpeng.advisor.model.Ingredient;
import com.linpeng.advisor.model.User;

/**
 * application global configuration class
 * 
 * @author linpeng
 *
 */
public class BaseConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setBaseViewPath("/views");
		me.setViewType(ViewType.FREE_MARKER);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/login", LoginController.class, "login");
		me.add("/ingredient", IngredientsController.class, "ingredient");
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
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// me.add(new AuthInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
	}
}
