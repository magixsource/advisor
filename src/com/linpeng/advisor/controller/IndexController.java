package com.linpeng.advisor.controller;

import com.jfinal.core.Controller;
import com.linpeng.advisor.annotation.AopIgnore;
import com.linpeng.advisor.interceptor.AuthInterceptor;

/**
 * Application index controller
 * 
 * @author linpeng
 *
 */
@AopIgnore(AuthInterceptor.class)
public class IndexController extends Controller {

	public void index() {

	}
}
