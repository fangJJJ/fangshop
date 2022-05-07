package com.fang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fang.bo.MemberBO;
import com.fang.model.Member;
import com.fang.util.CommonUtil;
import com.fang.exception.DataCheckedException;

@Controller
@RequestMapping("member")
public class MemberController extends ControllerBase{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("memberBO")
	private MemberBO memberBO;
	
	@RequestMapping(value="/doRegister")
	public String doRegister() {
		return Descriptor.Page_Register;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView register(ModelAndView modelAndView, @ModelAttribute Member pageMember) {
		try {
			if(pageMember == null) {
				logger.error("register Fail , pageMember is null.");
				throw new Exception("register Fail , pageMember is null.");
			}
			//是否已是會員
			Member member = memberBO.getMemberByEmail(pageMember.getEmail());
			if(member != null) {
				throw new DataCheckedException("您已是會員，請重新登入。");
			}
			//會員基本資料驗證
			memberBO.checkMemberData(pageMember);
			//註冊會員
			memberBO.createMember(pageMember);
		}catch(DataCheckedException de) {	
			logger.info("register 會員資料驗證錯誤 (Email="+pageMember.getEmail()+")", de.getMessage());
			modelAndView.addObject(Descriptor.Obj_Member, pageMember);
			modelAndView.addObject(ControllerBase.MESSAGE_ERROR, de.getMessage());
			modelAndView.setViewName(Descriptor.Page_Register);
			return modelAndView;
		}catch(Exception e) {
			logger.error("register Fail (Email="+pageMember.getEmail()+")", e);
			modelAndView.addObject(ControllerBase.MESSAGE_ERROR, "註冊失敗，請稍後再試。");
			return forwardError(modelAndView);
		}
		//註冊完成頁面
		modelAndView.setViewName(Descriptor.Page_Register_Fin);
		return modelAndView;
	}
	
	@RequestMapping(value="/doLogin")
	public String doLogin() {
		return Descriptor.Page_Login;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView login(ModelAndView modelAndView, HttpServletRequest request, @ModelAttribute Member pageMember) {
		try {
			if(pageMember == null) {
				logger.error("login Fail , pageMember is null.");
				throw new Exception("login Fail , pageMember is null.");
			}
			Member member = memberBO.getMemberByEmail(pageMember.getEmail());
			if(member == null) {
				throw new DataCheckedException("此帳號不存在，請註冊會員。");
			}
			//登入程序
			memberBO.doLoginProcess(request, pageMember);
		}catch(DataCheckedException de) {	
			logger.info("login Fail 會員資料驗證錯誤 (Email="+pageMember.getEmail()+")", de.getMessage());
			modelAndView.addObject(Descriptor.Obj_Member, pageMember);
			modelAndView.addObject(ControllerBase.MESSAGE_ERROR, de.getMessage());
			modelAndView.setViewName(Descriptor.Page_Login);
			return modelAndView;
		}catch(Exception e) {
			logger.error("login Fail (Email="+pageMember.getEmail()+")", e);
			modelAndView.addObject(ControllerBase.MESSAGE_ERROR, "登入失敗，請稍後再試。");
			return forwardError(modelAndView);
		}
		//登入完成，去首頁
		return forwardIndex(modelAndView);
	}
	
	@RequestMapping(value="/socialLogin")
	public ModelAndView socialLogin(ModelAndView modelAndView, HttpServletRequest request) {
		try {
			memberBO.doSocialLoginProcess(request);
		}catch(Exception e) {
			logger.error("socialLogin Fail", e);
			modelAndView.addObject(ControllerBase.MESSAGE_ERROR, "登入失敗，請稍後再試。");
			modelAndView.setViewName(Descriptor.Page_Login);
			return modelAndView;
		}
		//登入完成，去首頁
		return forwardIndex(modelAndView);
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && CommonUtil.getCurrentMember(session) != null) {
			CommonUtil.clearCurrentMember(session);
		}
		return forwardIndex();
	}
	
	private static class Descriptor {
		/*
		 * 頁面路徑
		 */
		public final static String Page_Register = "member.register.page";
		public final static String Page_Register_Fin = "member.register.fin.page";
		public final static String Page_Login = "member.login.page";
		/*
		 * 頁面物件
		 */
		public final static String Obj_Member = "member";
	}
}
