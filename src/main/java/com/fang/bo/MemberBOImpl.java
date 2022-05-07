package com.fang.bo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fang.constants.SocialSour;
import com.fang.dao.MemberDAO;
import com.fang.model.Member;
import com.fang.service.SocialService;
import com.fang.util.CommonUtil;
import com.fang.util.StringUtil;
import com.fang.exception.DataCheckedException;
import com.fang.factory.SocialFactory;

@Service("memberBO")
public class MemberBOImpl implements MemberBO {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	private MemberDAO memberDAO;
	
	/*
	 * 取得會員資料by Email
	 * @param email
	 * @return member
	 * @throws DataCheckedException, Exception
	 */
	@Override
	public Member getMemberByEmail(String email) throws DataCheckedException, Exception {
		if(StringUtils.isBlank(email)){
			throw new DataCheckedException("Email不能為空");
		}
		try{
			Member member = memberDAO.selectMemberByEmail(email);
			return member;			
		}catch(Exception e){
			logger.error("getMemberByEmail Fail (email = "+email+")", e);
			throw e;
		}
	}
	
	/**
	 * 驗證會員資料
	 * @param pageMember
	 * @throws DataCheckedException
	 */
	@Override
	public void checkMemberData(Member pageMember) throws DataCheckedException {
		//檢查Email
		if(StringUtils.isBlank(pageMember.getEmail())) {
			throw new DataCheckedException("Email不能為空");
		}
		String emailErrMsg = StringUtil.checkEmail(pageMember.getEmail());
		if(StringUtils.isNotBlank(emailErrMsg)) {
			throw new DataCheckedException(emailErrMsg);
		}
		//檢查密碼
		if(StringUtils.isBlank(pageMember.getPassword())) {
			throw new DataCheckedException("密碼不能為空");
		}
		if (!pageMember.getPassword().matches("^[0-9a-zA-Z]+$")) {
			throw new DataCheckedException("密碼請用英文或數字組合") ;
		} else if (pageMember.getPassword().length() > 30) {
			throw new DataCheckedException("密碼長度必須小於30") ;
		}
		//檢查手機
		if(StringUtils.isNotBlank(pageMember.getMobile())) {
			String mobileErrMsg = StringUtil.checkMobile(pageMember.getMobile());
			if(StringUtils.isNotBlank(mobileErrMsg)) {
				throw new DataCheckedException(mobileErrMsg);
			}
		}
		//檢查姓名
		if(StringUtils.isNotBlank(pageMember.getName())) {
			if (pageMember.getName().length() > 30) {
				throw new DataCheckedException("中文姓名必須小於10個字，英文姓名必須小於30個字");
			}
		}
	}
	
	/**
	 * 註冊會員
	 * @param pageMember
	 * @throws Exception
	 */
	@Override
	public void createMember(Member pageMember) throws Exception {
		if(pageMember == null) {
			logger.error("createMember Fail , pageMember is null.");
			throw new Exception("createMember Fail , pageMember is null.");
		}
		String prefixLog = "(Email="+pageMember.getEmail()+") ";
		logger.info(prefixLog + "register Start...");
		try{
			pageMember.setRegisterTime(new StringUtil().getCurrentTimestamp());
			memberDAO.insertMember(pageMember);
		}catch(Exception e){
			logger.error("insertMember Fail (member = "+pageMember.getEmail()+")", e);
			throw new Exception("insertMember Fail (member = "+pageMember.getEmail()+")", e);
		}
		logger.info(prefixLog + "register Success.");
	}
	
	/**
	 * 登入會員
	 * @param request
	 * @param pageMember
	 * @throws DataCheckedException
	 */
	@Override
	public void doLoginProcess(HttpServletRequest request, Member pageMember) throws DataCheckedException {
		//檢核會員帳號密碼資料
		this.checkMemberData(pageMember);
		//帳號密碼是否正確
		Member dbMember = memberDAO.selectMemberByEmailAndPassword(pageMember);
		if(dbMember == null) {
			throw new DataCheckedException("E-mail或密碼錯誤");
		}
		//更新會員登入時間
		try{
			dbMember.setLoginTime(new StringUtil().getCurrentTimestamp());
			memberDAO.updateMemberByPrimaryKey(dbMember);
		}catch(Exception e) {
			logger.error("updateMemberByPrimaryKey Fail 更新會員登入時間失敗, "+pageMember.toString(), e);
		}
		//取得最新的會員資料
		dbMember = memberDAO.selectMemberByEmailAndPassword(pageMember);
		//member session
		HttpSession session = request.getSession(false);
		CommonUtil.setCurrentMember(session, dbMember);
	}
	
	/**
	 * 社交軟體登入會員
	 * @param request
	 * @throws Exception
	 */
	@Override
	public void doSocialLoginProcess(HttpServletRequest request) throws Exception {
		String sour = request.getParameter("socialsour");
		Member member = null;
		//去登入的應用程式取回使用者資料
		try {
			SocialSour socialSour = SocialSour.getBySour(sour);
			SocialService socialService = SocialFactory.getSocialBean(socialSour);
			member = socialService.getSocialMemberInfo(request);
		}catch(Exception e) {
			logger.error("getSocialMemberInfo fail", e);
			throw new Exception("getSocialMemberInfo fail", e);
		}
		if(member == null){
			throw new Exception("getSocialMemberInfo無法取得登入資料, member為null");
		}else{
			if(StringUtils.isBlank(member.getEmail())){
				throw new Exception("getSocialMemberInfo登入會員email為空");
			}
		}
		//檢查會員是否存在，不存在就直接幫忙註冊會員
		Member dbMember = this.getMemberByEmail(member.getEmail());
		if(dbMember == null) {
			dbMember = new Member();
//			dbMember.setName(member.getName());
			dbMember.setEmail(member.getEmail());
			dbMember.setPassword(StringUtil.makPswd(20));
			this.createMember(dbMember);
		}
		//登入會員
		this.doLoginProcess(request, dbMember);
	}

}
