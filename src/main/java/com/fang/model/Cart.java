package com.fang.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	
	private String receiverName;
	private String receiverPhone;
	private String receiverAddress;
	private List<ShoppingProduct> shoppingProductList = new ArrayList<ShoppingProduct>();
	
	
	public List<ShoppingProduct> getShoppingProductList() {
		return shoppingProductList;
	}


	public void setShoppingProductList(List<ShoppingProduct> shoppingProductList) {
		this.shoppingProductList = shoppingProductList;
	}
	
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	/**
	 * 購物車新增商品
	 * @param shoppingProduct
	 */
	public void addProduct(ShoppingProduct shoppingProduct) {
		if(containShoppingProduct(shoppingProduct)) {
			//購物車已有此商品，商品數量
			addShoppingProductQty(shoppingProduct);
		}else {
			//購物車沒有此商品，直接加商品
			this.shoppingProductList.add(shoppingProduct);
		}
	}
	
	/**
	 * 購物車是否已存在該商品
	 * @param shoppingProduct
	 * @return boolean
	 */
	public boolean containShoppingProduct(ShoppingProduct shoppingProduct) {
		boolean isContain = this.shoppingProductList.stream()
				.anyMatch(product -> product.getProdid() == shoppingProduct.getProdid());
		return isContain;
	}
	
	/**
	 * 購物車刪除商品
	 * @param shoppingProduct
	 */
	public void removeProduct(ShoppingProduct shoppingProduct) {
		this.shoppingProductList.removeIf(product -> product.getProdid() == shoppingProduct.getProdid());
	}
	
	/**
	 * 取得購物車中的商品
	 * @param prodid
	 * @return ShoppingProduct
	 */
	public ShoppingProduct getShoppingProduct(int prodid) {
		return this.shoppingProductList.stream().filter(product -> product.getProdid() == prodid).findFirst().orElse(null);
	}
	
	/**
	 * 變更購物車商品數量
	 * @param ShoppingProduct
	 */
	public void changeShoppingProductQty(ShoppingProduct shoppingProduct, int qty) {
		this.shoppingProductList.stream().forEach(product -> {
			if(product.getProdid() == shoppingProduct.getProdid()) {
				product.setQty(qty);
				product.setAmount(product.getPrice()*qty);
			}
		});
	}
	
	/**
	 * 增加同商品的數量
	 * @param ShoppingProduct
	 */
	public void addShoppingProductQty(ShoppingProduct shoppingProduct) {
		this.shoppingProductList.stream().forEach(product -> {
			if(product.getProdid() == shoppingProduct.getProdid()) {
				int newQty = product.getQty() + shoppingProduct.getQty();
				product.setQty(newQty);
				product.setAmount(product.getPrice()*newQty);
			}
		});
	}
	
	/**
	 * 取得購物車商品總數量
	 * @return int
	 */
	public int getTotalQty() {
		return getShoppingProductList().stream().mapToInt(product -> product.getQty()).sum();
	}
	
	/**
	 * 取得購物車商品總金額
	 * @return int
	 */
	public int getTotalAmount() {
		return getShoppingProductList().stream().mapToInt(product -> product.getQty()*product.getPrice()).sum();
	}
}
