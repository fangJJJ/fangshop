package com.fang.bo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fang.dao.CategoryDAO;
import com.fang.dao.ProductDAO;
import com.fang.model.BatchProduct;
import com.fang.model.Category;
import com.fang.model.Product;
import com.fang.util.StringUtil;

@RunWith(MockitoJUnitRunner.class)
public class ProductBOImplTest {
	
	@Mock
	private ProductDAO productDAO;
	
	@Mock
	private CategoryDAO categoryDAO;
	
	@Mock
	private Category category;
	
	@Mock
	private BatchProduct mockBatchProduct;
	
	@InjectMocks
	private ProductBOImpl productBO;
	
	private Product product;
	private List<BatchProduct> batchProductList = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		//product
		product = new Product();
		product.setProdid(26);
		product.setName("test");
		product.setPrice(500);
		product.setBegindate(StringUtil.convertStringToTimestamp("2022-01-09 09:00"));
		product.setEnddate(StringUtil.convertStringToTimestamp("2023-01-09 18:00"));
		product.setStock(40);
		product.setCategoryid(1);
		product.setPicurl("https://img.udnfunlife.com/image/product/DS003604/APPROVED/DU00051146/20191016185207463_500.jpg");
		//batchProductList
		BatchProduct p1 = new BatchProduct();
		p1.setName("hhh");
		p1.setPrice(300);
		p1.setBegindate("2022-04-15 08:00");
		p1.setEnddate("2022-07-14 08:00");
		p1.setStock(20);
		p1.setCategoryid(1);
		p1.setPicurl("https://img.udnfunlife.com/image/product/DS003604/APPROVED/DU00051146/20191016185207463_500.jpg");
		BatchProduct p2 = new BatchProduct();
		p2.setName("兩用飲料杯套");
		p2.setPrice(400);
		p2.setBegindate("2022-04-28 08:00");
		p2.setEnddate("2023-04-27 08:00");
		p2.setStock(20);
		p2.setCategoryid(4);
		p2.setPicurl("https://img.udnfunlife.com/image/product/DS003604/APPROVED/DU00051146/20191016185207463_500.jpg");
		batchProductList.add(p1);
		batchProductList.add(p2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getProductTest() {
		when(productDAO.selectProductByProdid(26)).thenReturn(product);
		Product dbProduct = productBO.getProduct(26);
		assertEquals(product, dbProduct);
		verify(productDAO).selectProductByProdid(26);
	}
	
	@Test
	public void updateProductTest() {
		given(productDAO.selectProductByProdid(26)).willReturn(product);
		product.setPrice(600);
		productBO.updateProduct(product);
		Product dbProduct = productBO.getProduct(26);
		assertEquals(600, dbProduct.getPrice());
		assertNotNull(dbProduct.getUpdatetime());
		then(productDAO).should().updateProductByPrimaryKey(dbProduct);
		then(productDAO).should().selectProductByProdid(26);
	}
	
	@Test
	public void checkProductFieldsTest() {
		given(categoryDAO.selectCategory(10)).willReturn(null);
		product.setBegin_date("2022-01-09");
		product.setBegin_time("09:00");
		product.setEnd_date("2021-01-09");
		product.setEnd_time("18:00");
		product.setCategoryid(10);
		Map<String, String> error = productBO.checkProductFields(product);
		assertNotNull(error);
		assertEquals("無此商品類別或已下架", error.get("categoryid"));
		assertEquals("商品上架結束時間必須晚於商品上架開始時間", error.get("end_date"));
		then(categoryDAO).should().selectCategory(10);
	}
	
	@Test
	public void checkBatchProductFieldsTest() {
		given(categoryDAO.selectCategory(anyInt())).willReturn(category);
		boolean hasError = productBO.checkBatchProductFields(batchProductList);
		assertFalse(hasError);
		then(categoryDAO).should(times(2)).selectCategory(anyInt());
	}
	
	@Test
	public void saveBatchProductTest() {
		int row = productBO.saveBatchProduct(batchProductList);
		assertEquals(2, row);
	}
}
