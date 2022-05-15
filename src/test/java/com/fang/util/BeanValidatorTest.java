package com.fang.util;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fang.model.Product;

public class BeanValidatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void validateTest() {
		Product product = new Product();
		Map<String, String> errorMap = BeanValidator.validate(product);
		System.out.println("errorMap = "+errorMap);
		assertNotNull(errorMap);
		assertEquals(4, errorMap.size());
		assertEquals("商品名稱不能為空", errorMap.get("name"));
	}

}
