package com.fang.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OrderItem {
	
	private int ordItemNum;
	private int ordnum;
	private int prodid;
	private String prodName;
	private int price;
	private int qty;
	private int amount;
	
	public OrderItem() {}
	
	public OrderItem(ShoppingProduct shoppingProduct) {
		this.prodid = shoppingProduct.getProdid();
		this.prodName = shoppingProduct.getName();
		this.price = shoppingProduct.getPrice();
		this.qty = shoppingProduct.getQty();
		this.amount = shoppingProduct.getPrice()*shoppingProduct.getQty();
	}
	
	public int getOrdItemNum() {
		return ordItemNum;
	}
	public void setOrdItemNum(int ordItemNum) {
		this.ordItemNum = ordItemNum;
	}
	public int getOrdnum() {
		return ordnum;
	}
	public void setOrdnum(int ordnum) {
		this.ordnum = ordnum;
	}
	public int getProdid() {
		return prodid;
	}
	public void setProdid(int prodid) {
		this.prodid = prodid;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override  
	public String toString () {  
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);  
	} 
}
