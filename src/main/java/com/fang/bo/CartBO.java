package com.fang.bo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fang.exception.DataCheckedException;
import com.fang.model.Cart;
import com.fang.model.Member;
import com.fang.model.Order;
import com.fang.model.ShoppingProduct;

public interface CartBO {
	
	/**
	 * 加入購物車，將商品轉為購物車商品
	 * @param request
	 * @return ShoppingProduct
	 */
	public ShoppingProduct processShoppingProduct(HttpServletRequest request) throws DataCheckedException;
	
	/**
	 * 將cart轉為map，回傳json用
	 * @param cart
	 * @return Map<String, Object>
	 */
	public Map<String, Object> changeCartToMap(Cart cart);
	
	/**
	 * 檢查request來的商品編號及數量，並轉為int
	 * @param request, checkQty
	 * @return Map<String, Object>
	 */
	public Map<String, Object> checkProductData(HttpServletRequest request, boolean checkQty) throws DataCheckedException;
	
	/**
	 * 檢查和儲存收件人資訊
	 * @param request, currentCart
	 * @throws DataCheckedException
	 */
	public void setReceiverData(HttpServletRequest request, Cart currentCart) throws DataCheckedException;
	
	/**
	 * 購物車轉為訂單
	 * @param currentCart, currentMember
	 * @return Order
	 */
	public Order processOrder(Cart currentCart, Member currentMember);
	
	/**
	 * 檢查購物車商品
	 * @param currentCart
	 * @return errorMessage
	 */
	public String checkShoppingProductList(Cart currentCart);
	
}
