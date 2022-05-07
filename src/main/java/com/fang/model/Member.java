package com.fang.model;

import java.sql.Timestamp;

public class Member {
	
	private long memNum;
	private String name;
	private String email;
	private String password;
	private String mobile;
	private Timestamp registerTime;
	private Timestamp loginTime;
	private Timestamp updateTime;
	
	public long getMemNum() {
		return memNum;
	}
	public void setMemNum(long memNum) {
		this.memNum = memNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String toString() {
		return "Member = { memNum: "+getMemNum()+", email: "+getEmail()+" }";
	}
}
