package com.fang.model;

import java.sql.Timestamp;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fang.util.StringUtil;

public class Product {
	
	private int prodid;
	
	@NotBlank(message="商品名稱不能為空")
	@Size(min=1, max=50, message="商品名稱長度必須小於50")
	private String name;
	
	@NotNull(message="商品金額不能為空")
	@Digits(integer=10, fraction=0, message="商品金額必須為數字")
	@Positive(message="商品金額必須為正整數")
	private int price;
	
	@Size(min=0, max=200, message="商品描述長度必須小於200")
	private String description;
	
	private Timestamp begindate;
	private Timestamp enddate;
	
	@NotNull(message="商品庫存不能為空")
	@Digits(integer=10, fraction=0, message="商品庫存必須為數字")
	@PositiveOrZero(message="商品庫存必須為正整數")
	private int stock;
	
	@NotNull(message="商品類別不能為空")
	@Positive(message="商品類別必須為正整數")
	private int categoryid;
	
	private Timestamp createtime;
	private Timestamp updatetime;
	
	@NotBlank(message="商品圖片網址不能為空")
	@Size(min=1, max=200, message="商品圖片網址長度必須小於200")
	private String picurl;
	
	//---商品上架時間分為Date+Time，頁面使用
	private String begin_date;
	private String begin_time;
	private String end_date;
	private String end_time;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getBegindate() {
		return begindate;
	}
	public void setBegindate(Timestamp begindate) {
		this.begindate = begindate;
	}
	public Timestamp getEnddate() {
		return enddate;
	}
	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	@Override  
	public String toString () {  
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);  
	}
	
	/**
	 * 是否在開賣期間
	 * @return boolean
	 */
	public boolean isOnSale() {
		Timestamp now = new StringUtil().getCurrentTimestamp();
		if(now.after(getBegindate()) && now.before(getEnddate())) {
			return true;
		}	
		return false;
	}
	
	/**
	 * 是否還有庫存
	 * @return boolean
	 */
	public boolean hasStock() {
		if(getStock() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 庫存是否夠買
	 * @return boolean
	 */
	public boolean canBuy(int qty) {
		if(qty <= getStock()) {
			return true;
		}
		return false;
	}
}
