package com.fang.bo;


import java.util.List;

import com.fang.exception.DataCheckedException;
import com.fang.model.Member;
import com.fang.model.Order;

public interface OrderBO {
	
	/**
	 * 成立訂單
	 * @param order, member
	 * @throws DataCheckedException 
	 */
	public String saveOrder(Order order, Member member) throws DataCheckedException;
	
	/**
	 * 扣商品庫存
	 * @param order, member
	 * @throws DataCheckedException 
	 */
	public void deductStock(Order order, Member member) throws DataCheckedException;
	
	/**
	 * 以訂單流水號ordnum取得order
	 * @param ordnum
	 * @return Order 
	 */
	public Order getOrder(int ordnum);
	
	/**
	 * 取得下一個訂單編號
	 * OD + 日期 + 隨機4碼
	 * @return String
	 */
	public String getNextOrdid();
	
	/**
	 * 以會員編號取得訂單列表
	 * @param memnum
	 * @return List<Order> 
	 */
	public List<Order> getOrderWithItemList(long memnum);
}
