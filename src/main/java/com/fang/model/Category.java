package com.fang.model;

import java.sql.Timestamp;

public class Category {
	
	private int categoryid;
	private String name;
	private String sts;
	private Timestamp createtime;
	private Timestamp updatetime;
	
	/**
	 * 狀態-上架
	 */
	public static final String STS_UP = "U";
    /**
     * 狀態-下架
     */
	public static final String STS_DOWN = "D";
	
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
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
}
