package com.fang.util;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void checkMobileTest() {
		String errMsg = StringUtil.checkMobile("091234");
		assertTrue(StringUtils.isNotBlank(errMsg));
		assertEquals("手機長度須為10個數字", errMsg);
	}
	
	@Test
	public void checkEmailTest() {
		String errMsg = StringUtil.checkEmail("testgmail.com");
		assertTrue(StringUtils.isNotBlank(errMsg));
	}
	
	@Test
	public void makPswdTest() {
		String password = StringUtil.makPswd(6);
		assertTrue(StringUtils.isNotBlank(password));
		assertEquals(6, password.length());
	}
	
	@Test
	public void lpadTest() {
		String str = StringUtil.lpad("h!", 6, "O");
		assertEquals("OOOOh!", str);
	}
	
	@Test
	public void convertStringToTimestampTest() throws ParseException {
		String date = "2022-01-01";
		String time = "09:00";
		Timestamp timestamp1 = StringUtil.convertStringToTimestamp(date, time);
		assertEquals(Timestamp.valueOf("2022-01-01 09:00:00"), timestamp1);
	}

}
