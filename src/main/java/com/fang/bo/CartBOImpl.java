package com.fang.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fang.exception.DataCheckedException;
import com.fang.model.Cart;
import com.fang.model.Member;
import com.fang.model.Order;
import com.fang.model.OrderItem;
import com.fang.model.Product;
import com.fang.model.ShoppingProduct;
import com.fang.util.StringUtil;

@Service("cartBO")
public class CartBOImpl implements CartBO {
	
	@Autowired
	private ProductBO productBO;
	
	/**
	 * 加入購物車，將商品轉為購物車商品
	 * @param request
	 * @return ShoppingProduct
	 */
	@Override
	public ShoppingProduct processShoppingProduct(HttpServletRequest request) throws DataCheckedException{
		Map<String, Object> parameterMap = this.checkProductData(request, true);
		int qty = (int) parameterMap.get("qty");
		int prodid = (int) parameterMap.get("prodid");
		Product product = productBO.getOnSaleProduct(prodid);  
		if(product == null) {
			throw new DataCheckedException("商品已下架");
		}else if(!product.hasStock()) {
			throw new DataCheckedException("此商品無庫存");
		}else if(!product.canBuy(qty)) {
			throw new DataCheckedException("購買數量大於庫存量");
		}
		ShoppingProduct shoppingProduct = new ShoppingProduct(product, qty);
		return shoppingProduct;
	}
	
	/**
	 * 將cart轉為map，回傳json用
	 * @param cart
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> changeCartToMap(Cart cart){
		Map<String, Object> data = new HashMap<>();
		if(cart != null
				&& cart.getShoppingProductList() != null
				&& cart.getShoppingProductList().size() > 0) {
			data.put("shoppingProductList", cart.getShoppingProductList());
			data.put("totalAmount", cart.getTotalAmount());	
			data.put("totalQty", cart.getTotalQty());
		}
		return data;
	}
	
	/**
	 * 檢查request來的商品編號及數量，並轉為int
	 * @param request, checkQty
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> checkProductData(HttpServletRequest request, boolean checkQty) throws DataCheckedException{
		Map<String, Object> returnMap = new HashMap<>();
		String strProdid = request.getParameter("prodid");
		if(StringUtils.isBlank(strProdid) || !StringUtils.isNumeric(strProdid)) {
			throw new DataCheckedException("商品無效");
		}
		int prodid = Integer.parseInt(strProdid);
		returnMap.put("prodid", prodid);
		//是否需要檢查qty
		if(checkQty) {
			String strQty = request.getParameter("qty");
			if(StringUtils.isBlank(strQty) || !StringUtils.isNumeric(strQty)) {
				throw new DataCheckedException("商品數量無效");
			}
			int qty = Integer.parseInt(strQty);
			if(qty < 1) {
				throw new DataCheckedException("商品購買數量必須大於等於1");
			}
			returnMap.put("qty", qty);
		}
		return returnMap;
	}
	
	
	
	/**
	 * 購物車轉為訂單
	 * @param currentCart, currentMember
	 * @return Order
	 */
	public Order processOrder(Cart currentCart, Member currentMember) {
		List<OrderItem> itemList = new ArrayList<OrderItem>();
		List<ShoppingProduct> shoppingProductList = currentCart.getShoppingProductList();
		for(ShoppingProduct shoppingProduct : shoppingProductList) {
			OrderItem item = new OrderItem(shoppingProduct);
			itemList.add(item);
		}
		Order order = new Order(currentCart);
		order.setMemNum(currentMember.getMemNum());
		order.setItems(itemList);
		return order;
	}
	
	/**
	 * 檢查購物車商品
	 * @param currentCart
	 * @return errorMessage
	 */
	@Override
	public String checkShoppingProductList(Cart currentCart){
		StringBuffer errorMessage = new StringBuffer();
		Iterator<ShoppingProduct> iter = currentCart.getShoppingProductList().iterator();
		while(iter.hasNext()) {
			ShoppingProduct shoppingProduct = iter.next();
			if(shoppingProduct.getQty() < 1) {
				errorMessage.append(shoppingProduct.getName() + " 購買數量需大於等於1 <br />");
				iter.remove();
				continue;
			}
			Product product = productBO.getOnSaleProduct(shoppingProduct.getProdid()); 
			if(product == null) {
				errorMessage.append(shoppingProduct.getName() + " 已下架 <br />");
				iter.remove();
				continue;
			}
			if(!product.hasStock()) {
				errorMessage.append(shoppingProduct.getName() + " 已無庫存 <br />");
				iter.remove();
				continue;
			}
			if(!product.canBuy(shoppingProduct.getQty())) {
				errorMessage.append(shoppingProduct.getName() + " 購買數量大於庫存量 <br />");
				iter.remove();
				continue;
			}
			//將購物車商品更新到最新資訊
			shoppingProduct.setName(product.getName());
			shoppingProduct.setPrice(product.getPrice());
		}
		return errorMessage.toString();
	}
	
	/**
	 * 檢查和儲存收件人資訊
	 * @param request, currentCart
	 * @throws DataCheckedException
	 */
	public void setReceiverData(HttpServletRequest request, Cart currentCart) throws DataCheckedException{
		String receiverName = request.getParameter("receiverName");
		String receiverPhone = request.getParameter("receiverPhone");
		String receiverAddress = request.getParameter("receiverAddress");
		currentCart.setReceiverName(receiverName);
		currentCart.setReceiverPhone(receiverPhone);
		currentCart.setReceiverAddress(receiverAddress);
		if(StringUtils.isBlank(receiverName)) {
			throw new DataCheckedException("收件人姓名不能為空");
		}else if(receiverName.length() > 30) {
			throw new DataCheckedException("收件者中文姓名必須小於10個字，英文姓名必須小於30個字");
		}
		if(StringUtils.isBlank(receiverPhone)) {
			throw new DataCheckedException("收件人電話不能為空");
		}
		String mobileErrMsg = StringUtil.checkMobile(receiverPhone);
		if(StringUtils.isNotBlank(mobileErrMsg)) {
			throw new DataCheckedException(mobileErrMsg);
		}
		if(StringUtils.isBlank(receiverAddress)) {
			throw new DataCheckedException("收件人地址不能為空");
		}else if(receiverAddress.length() > 200) {
			throw new DataCheckedException("收件者中文地址必須小於65個字，英文姓名必須小於200個字");
		}
	}
}
