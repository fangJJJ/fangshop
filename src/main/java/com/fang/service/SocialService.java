package com.fang.service;

import javax.servlet.http.HttpServletRequest;

import com.fang.model.Member;

public interface SocialService {
	
	/**
	 * post至登入時所選擇的應用程式(Facebook、Line等等)，並取回資料
	 */
	public Member getSocialMemberInfo(HttpServletRequest request) throws Exception;
	
}
