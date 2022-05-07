package com.fang.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BatchProduct {
	
	@NotBlank(message="商品名稱不能為空")
	@Size(min=1, max=50, message="商品名稱長度必須小於50")
	private String name;
	
	@NotNull(message="商品金額不能為空")
	@Digits(integer=10, fraction=0, message="商品金額必須為數字")
	@Positive(message="商品金額必須為正整數")
	private Integer price;
	
	@Size(min=0, max=200, message="商品描述長度必須小於200")
	private String description;
	
	@NotBlank(message="商品上架開始時間不能為空")
	private String begindate;
	
	@NotBlank(message="商品上架結束時間不能為空")
	private String enddate;
	
	@NotNull(message="商品庫存不能為空")
	@Digits(integer=10, fraction=0, message="商品庫存必須為數字")
	@PositiveOrZero(message="商品庫存必須為正整數")
	private Integer stock;
	
	@NotNull(message="商品類別不能為空")
	@Positive(message="商品類別必須為正整數")
	private Integer categoryid;
	
	@NotBlank(message="商品圖片網址不能為空")
	@Size(min=1, max=200, message="商品圖片網址長度必須小於200")
	private String picurl;
	
	//檢查欄位
	private Map<String, String> errorMap = new LinkedHashMap<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	@Override  
	public String toString () {  
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);  
	}
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}
}
