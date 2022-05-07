package com.fang.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fang.bo.CartBO;
import com.fang.bo.OrderBO;
import com.fang.bo.ProductBO;
import com.fang.exception.DataCheckedException;
import com.fang.model.Cart;
import com.fang.model.Member;
import com.fang.model.Order;
import com.fang.model.Product;
import com.fang.model.ShoppingProduct;
import com.fang.util.CommonUtil;

@Controller
@RequestMapping("cart")
public class CartController extends ControllerBase {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("cartBO")
	private CartBO cartBO;
	
	@Autowired
	@Qualifier("productBO")
	private ProductBO productBO;
	
	@Autowired
	@Qualifier("orderBO")
	private OrderBO orderBO;
	
	@RequestMapping(value="/ajaxAddCart", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String ajaxAddCart(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Map<String, Object> data = new HashMap<>();
		try {
			ShoppingProduct shoppingProduct = cartBO.processShoppingProduct(request);
			Cart sessionCart = CommonUtil.getCartSession(session);
			if(sessionCart == null) {
				sessionCart = new Cart();
				sessionCart.addProduct(shoppingProduct);
				CommonUtil.setCartSession(session, sessionCart);
			}else {
				sessionCart.addProduct(shoppingProduct);
			}
			data = cartBO.changeCartToMap(sessionCart);
		}catch(DataCheckedException de) {
			data.put("error", de.getMessage());
			return forwardJson(data);
		}catch(Exception e) {
			logger.error("ajaxAddCart fail", e);
			data.put("error", "加入購物車失敗");
			return forwardJson(data);
		}			
		return forwardJson(data);
	}
	
	@RequestMapping(value="/goCartPage")
	public String goCartPage() {
		return Descriptor.Page_Cart;
	}
	
	@RequestMapping(value="/showCart", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String showCart(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Cart cartSession = CommonUtil.getCartSession(session);
		Map<String, Object> data = cartBO.changeCartToMap(cartSession);
		return forwardJson(data);
	}
	
	@RequestMapping(value="/changeShoppingProductQty", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String changeShoppingProductQty(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<>();
		HttpSession session = request.getSession(false);
		try {
			Map<String, Object> parameterMap = cartBO.checkProductData(request, true);
			int prodid = (int) parameterMap.get("prodid");
			int qty = (int) parameterMap.get("qty");
			Cart cartSession = CommonUtil.getCartSession(session);
			if(cartSession == null) {
				throw new DataCheckedException("購物車為空");
			}
			ShoppingProduct shoppingProduct = cartSession.getShoppingProduct(prodid);
			if(shoppingProduct == null) {
				throw new DataCheckedException("購物車內無此商品");
			}
			Product product = productBO.getOnSaleProduct(prodid);  
			if(product == null) {
				throw new DataCheckedException("商品已下架，請移出購物車");
			}else if(!product.hasStock()) {
				throw new DataCheckedException("此商品無庫存，請移出購物車");
			}else if(!product.canBuy(qty)) {
				throw new DataCheckedException("購買數量大於庫存量");
			}
			cartSession.changeShoppingProductQty(shoppingProduct, qty);
			data = cartBO.changeCartToMap(cartSession);
		}catch(DataCheckedException de) {
			data.put("error", de.getMessage());
			return forwardJson(data);
		}catch(Exception e) {
			logger.error("changeShoppingProductQty fail", e);
			data.put("error", "變更商品數量失敗");
			return forwardJson(data);
		}
		return forwardJson(data);
	}
	
	@RequestMapping(value="/deleteShoppingProduct", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteShoppingProduct(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<>();
		HttpSession session = request.getSession(false);
		try {
			Map<String, Object> parameterMap = cartBO.checkProductData(request, false);
			int prodid = (int) parameterMap.get("prodid");
			Cart cartSession = CommonUtil.getCartSession(session);
			if(cartSession == null) {
				throw new DataCheckedException("購物車為空");
			}
			ShoppingProduct shoppingProduct = cartSession.getShoppingProduct(prodid);
			if(shoppingProduct == null) {
				throw new DataCheckedException("購物車內無此商品");
			}
			cartSession.removeProduct(shoppingProduct);
			if(cartSession.getShoppingProductList() != null && cartSession.getShoppingProductList().size() > 0) {
				data = cartBO.changeCartToMap(cartSession);
			}else {
				//若購物車已沒有商品，就清除cart session
				CommonUtil.clearCartSession(session);
			}
		}catch(DataCheckedException de) {
			data.put("error", de.getMessage());
			return forwardJson(data);
		}catch(Exception e) {
			logger.error("deleteShoppingProduct fail", e);
			data.put("error", "刪除購物車商品失敗");
			return forwardJson(data);
		}
		return forwardJson(data);
	}
	
	@RequestMapping(value="/goCheckOutPage")
	public String goCheckOutPage(HttpServletRequest request) {
		//進入最後一步前存下Token
		setToken(request);
		return Descriptor.Page_CheckOut;
	}
	
	@RequestMapping(value="/showCheckOut", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String showCheckOut(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Cart cartSession = CommonUtil.getCartSession(session);
		Map<String, Object> data = cartBO.changeCartToMap(cartSession);
		Member memberSession = CommonUtil.getCurrentMember(session);
		data.put("receiverName", (StringUtils.isNotBlank(cartSession.getReceiverName()))?cartSession.getReceiverName():memberSession.getName() );
		data.put("receiverPhone", (StringUtils.isNotBlank(cartSession.getReceiverPhone()))?cartSession.getReceiverPhone():memberSession.getMobile() );
		data.put("receiverAddress", (StringUtils.isNotBlank(cartSession.getReceiverAddress()))?cartSession.getReceiverAddress():"" );
		return forwardJson(data);
	}
	
	@RequestMapping(value="/saveOrder", method = RequestMethod.POST)
	public String saveOrder(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String ordid = "";
		try {
			//1.檢查token
			if(isTokenValid(request)) {
				session.removeAttribute("token");
			}else {
				throw new DataCheckedException("您有交易進行中或異常中斷，再次下訂可能會產生重複的訂單，請至「訂單列表」確認訂購是否完成");
			}
			Cart currentCart = CommonUtil.getCartSession(session);
			if(currentCart == null) {
				//若沒有cart session，回到購物車頁
				return Descriptor.Page_Cart;
			}
			//2.檢查和儲存收件人資料
			cartBO.setReceiverData(request, currentCart);		
			//3.檢查購物車商品
			String errMsg = cartBO.checkShoppingProductList(currentCart);
			if(StringUtils.isNotBlank(errMsg)) {
				throw new DataCheckedException(errMsg);
			}
			Member currentMember = CommonUtil.getCurrentMember(session);
			//4.將購物車轉為訂單
			Order order = cartBO.processOrder(currentCart, currentMember);
			//5.成立訂單
			ordid = orderBO.saveOrder(order, currentMember);
			//6.完成訂單後，刪除購物車session
			CommonUtil.clearCartSession(session);
		}catch(DataCheckedException de) {
			request.setAttribute(ControllerBase.MESSAGE_ERROR, de.getMessage());
			setToken(request);
			return Descriptor.Page_CheckOut;
		}catch(Exception e) {
			logger.error("saveOrder fail", e);
			return forwardError();
		}
		request.setAttribute("ordid", ordid);
		return Descriptor.Page_Finish;
	}
	
	private void setToken(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("token") != null) {
			session.removeAttribute("token");
		}
		UUID token = UUID.randomUUID();
		session.setAttribute("token", token.toString());
		request.setAttribute("token", token);
	}
	
	private boolean isTokenValid(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String serverToken = (String) session.getAttribute("token");
		if (StringUtils.isBlank(serverToken)) {
			return false;
		}
		String clientToken = request.getParameter("token");
		if(StringUtils.isBlank(clientToken)) {
			return false;
		}
		if(!serverToken.equals(clientToken)) {
			return false;
		}
		return true;
	}
	
	private static class Descriptor {
		/*
		 * 頁面路徑
		 */
		public final static String Page_Cart = "cart.page";
		public final static String Page_CheckOut = "checkout.page";
		public final static String Page_Finish = "finish.page";
	}
}
