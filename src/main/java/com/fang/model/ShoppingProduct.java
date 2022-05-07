package com.fang.model;

public class ShoppingProduct {
	
	private int prodid;
	private String name;
	private int price;
	private String picurl;
	private int qty;
	private int amount;

	public ShoppingProduct(Product product, int qty) {
		this.prodid = product.getProdid();
		this.name = product.getName();
		this.price = product.getPrice();
		this.picurl = product.getPicurl();
		this.qty = qty;
		this.amount = product.getPrice()*qty;
	}
	
	public ShoppingProduct(int prodid, String name, int price, int qty) {
		this.prodid = prodid;
		this.name = name;
		this.price = price;
		this.qty = qty;
		this.amount = price*qty;
	}
	
	public int getProdid() {
		return prodid;
	}
	public void setProdid(int prodid) {
		this.prodid = prodid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
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
}
