package com.fang.bo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fang.dao.OrderDAO;
import com.fang.dao.OrderItemDAO;
import com.fang.dao.ProductDAO;
import com.fang.exception.DataCheckedException;
import com.fang.model.Member;
import com.fang.model.Order;
import com.fang.model.OrderItem;
import com.fang.util.StringUtil;

@Service("orderBO")
public class OrderBOImpl implements OrderBO{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderItemDAO orderItemDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	/**
	 * 成立訂單
	 * @param order, member
	 * @throws DataCheckedException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public String saveOrder(Order order, Member member) throws DataCheckedException {
		String suffixLog = ", [訂購會員資料 Member Num:" + member.getMemNum() + "]";
		//扣庫存
		this.deductStock(order, member);
		logger.info("成立訂單 start..."+suffixLog);
		//insert Order
		String nextOrdid = this.getNextOrdid();
		order.setOrdid(nextOrdid);
		order.setCreateDate(new StringUtil().getCurrentTimestamp());
		orderDAO.insertOrder(order);
		int ordnum = order.getOrdnum();
		//insert OrderItem
		List<OrderItem> itemList = order.getItems();
		for(OrderItem item : itemList) {
			item.setOrdnum(ordnum);
			orderItemDAO.insertOrderItem(item);
		}
		logger.info("成立訂單 success, 訂單流水號："+ordnum+", 訂單編號："+nextOrdid+suffixLog);
		return nextOrdid;
	}
	
	/**
	 * 取得下一個訂單編號
	 * OD + 日期 + 隨機4碼
	 * @return String
	 */
	public String getNextOrdid() {
		return "OD" + StringUtil.getToday() + StringUtil.makPswd(4) ;
	}
	
	/**
	 * 扣商品庫存
	 * @param order, member
	 * @throws DataCheckedException 
	 */
	public void deductStock(Order order, Member member) throws DataCheckedException {
		String suffixLog = ", [訂購會員資料 Member Num:" + member.getMemNum() + "]";
		List<OrderItem> itemList = order.getItems();
		for(OrderItem item : itemList) {
			String dateLog = " 商品編號=" + item.getProdid() + ", 商品名稱=" + item.getProdName()
				+", 購買數量=" + item.getQty();
			int updateRow = productDAO.deductProductStock(item.getProdid(), item.getQty());
			if(updateRow == 0) {
				logger.warn("扣庫存失敗, "+dateLog+suffixLog);
				throw new DataCheckedException(item.getProdName()+"已無庫存");
			}
			logger.info("扣庫存成功, "+dateLog+suffixLog);
		}
	}
	
	/**
	 * 以訂單流水號ordnum取得order
	 * @param ordnum
	 * @return Order 
	 */
	public Order getOrder(int ordnum) {
		return orderDAO.selectOrderByPrimaryKey(ordnum);
	}
	
	public int insertOrder(Order order) {
		orderDAO.insertOrder(order);
		int ordnum = order.getOrdnum();
		return ordnum;
	}
	
	/**
	 * 以會員編號取得訂單列表
	 * @param memnum
	 * @return List<Order> 
	 */
	public List<Order> getOrderWithItemList(long memnum){
		return orderDAO.selectOrderWithItemsByMemnum(memnum);
	}
}
