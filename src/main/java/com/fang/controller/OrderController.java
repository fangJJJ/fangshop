package com.fang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fang.bo.OrderBO;
import com.fang.model.Member;
import com.fang.model.Order;
import com.fang.util.CommonUtil;

@Controller
@RequestMapping("order")
public class OrderController extends ControllerBase {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("orderBO")
	private OrderBO orderBO;
	
	@RequestMapping(value="/goOrderListPage")
	public ModelAndView goOrderListPage(HttpServletRequest request, ModelAndView modelAndView) {
		HttpSession session = request.getSession(false);
		Member memberSession = CommonUtil.getCurrentMember(session);
		List<Order> orderList = orderBO.getOrderWithItemList(memberSession.getMemNum());
		modelAndView.addObject("orderList", orderList);
		modelAndView.setViewName(Descriptor.Page_Order_List);
		return modelAndView;
	}	
	
	
	private static class Descriptor {
		/*
		 * 頁面路徑
		 */
		public final static String Page_Order_List = "order.list.page";
	}
}
