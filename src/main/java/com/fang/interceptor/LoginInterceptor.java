package com.fang.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fang.model.Member;
import com.fang.util.CommonUtil;

public class LoginInterceptor implements HandlerInterceptor {
	/**
	* 登入檢查, 判斷是否有member session
	*/
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		Member sessionMember = CommonUtil.getCurrentMember(session);
		if(sessionMember == null) {
			//若沒有session, 跳轉到登入頁面
            response.sendRedirect("/shop/member/doLogin.do");
			return false;
		}
    	return true;
    }
}
