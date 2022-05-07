package com.fang.dao;

import java.util.List;

import com.fang.model.Order;

public interface OrderDAO {
	
	public int insertOrder(Order order);
	
	public Integer selectNextOrdnum();
	
	public Order selectOrderByPrimaryKey(int ordnum);
	
	public List<Order> selectOrderWithItemsByMemnum(long memnum);

}
