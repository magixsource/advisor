package com.linpeng.advisor.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AdvisorUtilsTest {

	String strMore;
	String strLess;
	String strNo;

	@Before
	public void setUp() throws Exception {
		strMore = "fat,protein,dietary_fiber";
		strLess = "vpp,vitamin_b1,retinol_equivalent";
		strNo = "vitamin_c,natrium";
	}

	@Test
	public void testMoreAndLessAndNo() {
		String sql = AdvisorUtils
				.sqlConditionGennerate(strMore, strLess, strNo);
		System.out.println("   testMoreAndLessAndNo : " + sql);
		Assert.assertNotNull(sql);
	}

	@Test
	public void testMore() {
		String sql = AdvisorUtils.sqlConditionGennerate(strMore, null, null);
		System.out.println("   testMore : " + sql);
		Assert.assertNotNull(sql);
	}

	@Test
	public void testLess() {
		String sql = AdvisorUtils.sqlConditionGennerate(null, strLess, null);
		System.out.println("   testLess : " + sql);
		Assert.assertNotNull(sql);
	}

	@Test
	public void testNo() {
		String sql = AdvisorUtils.sqlConditionGennerate(null, null, strNo);
		System.out.println("   testNo : " + sql);
		Assert.assertNotNull(sql);
	}

	@Test
	public void testMoreAndLess() {
		String sql = AdvisorUtils.sqlConditionGennerate(strMore, strLess, null);
		System.out.println("   testMoreAndLess : " + sql);
		Assert.assertNotNull(sql);
	}

	@Test
	public void testMoreAndNo() {
		String sql = AdvisorUtils.sqlConditionGennerate(strMore, null, strNo);
		System.out.println("   testMoreAndNo : " + sql);
		Assert.assertNotNull(sql);
	}

	@Test
	public void testLessAndNo() {
		String sql = AdvisorUtils.sqlConditionGennerate(null, strLess, strNo);
		System.out.println("   testLessAndNo : " + sql);
		Assert.assertNotNull(sql);
	}

	public void testAllNo() {
		String sql = AdvisorUtils.sqlConditionGennerate(null, null, null);
		System.out.println("   testAllNo : " + sql);
		Assert.assertNotNull(sql);
	}

}
