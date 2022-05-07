package com.fang.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Order {
	
	private int ordnum;
	private String ordid;
	private Timestamp createDate;
	private long memNum;
	private String receiverName;
	private String receiverPhone;
	private String receiverAddress;
	private int totalAmount;
	private List<OrderItem> items = new ArrayList<OrderItem>();
	
	public Order() {}
	
	public Order(Cart cart) {
		this.receiverName = cart.getReceiverName();
		this.receiverPhone = cart.getReceiverPhone();
		this.receiverAddress = cart.getReceiverAddress();
		this.totalAmount = cart.getTotalAmount();
	}
	
	public int getOrdnum() {
		return ordnum;
	}
	public void setOrdnum(int ordnum) {
		this.ordnum = ordnum;
	}
	public String getOrdid() {
		return ordid;
	}
	public void setOrdid(String ordid) {
		this.ordid = ordid;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public long getMemNum() {
		return memNum;
	}
	public void setMemNum(long memNum) {
		this.memNum = memNum;
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
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	@Override  
	public String toString () {  
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);  
	}  
}
