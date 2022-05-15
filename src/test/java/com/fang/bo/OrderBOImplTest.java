package com.fang.bo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fang.dao.OrderDAO;
import com.fang.dao.OrderItemDAO;
import com.fang.dao.ProductDAO;
import com.fang.exception.DataCheckedException;
import com.fang.model.Member;
import com.fang.model.Order;
import com.fang.model.OrderItem;
import com.fang.util.StringUtil;

@RunWith(MockitoJUnitRunner.class)
public class OrderBOImplTest {
	
	@Mock
	private ProductDAO productDAO;
	
	@Mock
	private OrderDAO orderDAO;
	
	@Mock
	private OrderItemDAO orderItemDAO;
	
	@InjectMocks
	private OrderBOImpl orderBO;
	
	private Order order;
	private Member member;

	@Before
	public void setUp() throws Exception {
		OrderItem item1 = new OrderItem();
		item1.setProdid(1);
		item1.setProdName("name");
		item1.setQty(2);
		OrderItem item2 = new OrderItem();
		item2.setProdid(2);
		item2.setProdName("name2");
		item2.setQty(2);
		List<OrderItem> itemList = new ArrayList<>();
		itemList.add(item1);
		itemList.add(item2);
		order = new Order();
		order.setMemNum(1);
		order.setItems(itemList);
		member = new Member();
		member.setMemNum(1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getNextOrdidTest() {
		String nextOrdid = orderBO.getNextOrdid();
		assertTrue(nextOrdid.startsWith("OD"+StringUtil.getToday()));
	}
	
	@Test
	public void deductStockTest() throws DataCheckedException {
		given(productDAO.deductProductStock(anyInt(), anyInt())).willReturn(1);
		orderBO.deductStock(order, member);
		then(productDAO).should(times(2)).deductProductStock(anyInt(), anyInt());
	}
	
	@Test
	public void saveOrderTest() throws DataCheckedException {
		given(productDAO.deductProductStock(anyInt(), anyInt())).willReturn(1);
		String nextOrdid = orderBO.saveOrder(order, member);
		assertTrue(nextOrdid.startsWith("OD"+StringUtil.getToday()));
		then(productDAO).should(times(2)).deductProductStock(anyInt(), anyInt());
		then(orderDAO).should().insertOrder(order);
		then(orderItemDAO).should().insertOrderItem(order.getItems().get(0));
		then(orderItemDAO).should().insertOrderItem(order.getItems().get(1));
	}
	
	@Test
	public void getOrderWithItemListTest() {
		List<Order> list = new ArrayList<>();
		list.add(new Order());
		list.add(new Order());
		given(orderDAO.selectOrderWithItemsByMemnum(anyLong())).willReturn(list);
		List<Order> orderList = orderBO.getOrderWithItemList(anyLong());
		assertEquals(2, orderList.size());
		then(orderDAO).should().selectOrderWithItemsByMemnum(anyLong());
	}
}
