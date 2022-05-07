package com.fang.bo;

import com.fang.model.Member;

import javax.servlet.http.HttpServletRequest;

import com.fang.exception.DataCheckedException;

public interface MemberBO {
	
	/*
	 * 取得會員資料by Email
	 * @param email
	 * @return member
	 */
	public Member getMemberByEmail(String email) throws DataCheckedException, Exception;
	
	/**
	 * 驗證會員資料
	 * @param pageMember
	 * @throws DataCheckedException
	 */
	public void checkMemberData(Member pageMember) throws DataCheckedException;
	
	/**
	 * 註冊會員
	 * @param pageMember
	 * @throws Exception
	 */
	public void createMember(Member pageMember) throws Exception;
	
	/**
	 * 登入會員
	 * @param request
	 * @param pageMember
	 * @throws DataCheckedException
	 */
	public void doLoginProcess(HttpServletRequest request, Member pageMember) throws DataCheckedException;
	
	/**
	 * 社交軟體登入會員
	 * @param request
	 * @throws Exception
	 */
	public void doSocialLoginProcess(HttpServletRequest request) throws Exception;
}
