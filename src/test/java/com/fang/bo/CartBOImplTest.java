package com.fang.bo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.fang.exception.DataCheckedException;
import com.fang.model.Cart;
import com.fang.model.Member;
import com.fang.model.Order;
import com.fang.model.Product;
import com.fang.model.ShoppingProduct;

@RunWith(MockitoJUnitRunner.class)
public class CartBOImplTest {
	
	@Mock
	private ProductBO productBO;
	
	@InjectMocks
	private CartBOImpl cartBO;
	
	private Cart cart;
	private Member member;
	private MockHttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		ShoppingProduct p1 = new ShoppingProduct(11, "大和日記：西日本", 380, 2);
		ShoppingProduct p2 = new ShoppingProduct(23, "大谷翔平", 400, 2);
		ShoppingProduct p3 = new ShoppingProduct(12, "順勢溝通", 300, 2);
		ShoppingProduct p4 = new ShoppingProduct(23, "大谷翔平", 400, 1);
		cart = new Cart();
		cart.addProduct(p1);
		cart.addProduct(p2);
		cart.addProduct(p3);
		cart.addProduct(p4);
		cart.setReceiverName("阿方");
		cart.setReceiverPhone("0912345");
		member = new Member();
		member.setMemNum(1);
		request = new MockHttpServletRequest();
		request.setParameter("prodid", "11");
		request.setParameter("qty", "2");
		request.setParameter("receiverName", "阿方");
		request.setParameter("receiverPhone", "0912345678");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void cartTotalAmountTest() {
		assertEquals(2560, cart.getTotalAmount());
		assertEquals(7, cart.getTotalQty());
		assertEquals(3, cart.getShoppingProductList().size());
	}
	
	@Test
	public void shoppingProductListTest() {
		Product spyProduct = new Product();
		spyProduct.setStock(100);
		given(productBO.getOnSaleProduct(anyInt())).willReturn(spyProduct);
		String errMsg = cartBO.checkShoppingProductList(cart);
		assertTrue(StringUtils.isBlank(errMsg));
		then(productBO).should(times(3)).getOnSaleProduct(anyInt());
	}
	
	@Test
	public void processOrderTest() {
		Order order = cartBO.processOrder(cart, member);
		assertEquals(3, order.getItems().size());
		assertEquals(2560, order.getTotalAmount());
		assertEquals(order.getTotalAmount(), order.getItems().stream().mapToInt(item -> item.getQty()*item.getPrice()).sum());
		assertEquals("阿方", order.getReceiverName());
	}
	
	@Test(expected = DataCheckedException.class)
	public void testReceiverData() throws DataCheckedException{
		cartBO.setReceiverData(request, cart);
	}
}
