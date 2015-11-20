package com.linpeng.advisor.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringUtilsTest {

	String str = "1,2,3";
	String[] array = new String[] { "1", "2", "3" };

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArray2string() {
		Assert.assertTrue(StringUtils.array2string(array).equalsIgnoreCase(str));
		Assert.assertTrue(StringUtils.array2string(array, ";")
				.equalsIgnoreCase("1;2;3"));

		Assert.assertTrue(StringUtils.array2string(array, "','")
				.equalsIgnoreCase("1','2','3"));
	}

	@Test
	public void testString2Array() {
		Assert.assertTrue(StringUtils.string2array(str).length == 3);
		Assert.assertTrue(StringUtils.string2array("1,2,3,").length == 3);

		Assert.assertTrue(StringUtils.string2array("1;2;3;", ";").length == 3);
	}

}
