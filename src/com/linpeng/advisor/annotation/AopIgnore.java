package com.linpeng.advisor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jfinal.aop.Interceptor;

/**
 * Ignore JFinal intercepter or handler
 * 
 * @author linpeng
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface AopIgnore {
	Class<? extends Interceptor>[] value();
}
