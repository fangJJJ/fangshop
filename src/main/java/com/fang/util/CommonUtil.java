package com.fang.util;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fang.model.Cart;
import com.fang.model.Member;

public class CommonUtil {
	private static Logger logger = LogManager.getLogger(CommonUtil.class);
	private static final String MEMBER_SESSION = "member_session";
	private static final String CART_SESSION = "cart_session";
	
	/**
	 * 取得現行使用者帳號資料
	 */
	public static Member getCurrentMember(HttpSession session) {
		return (Member) session.getAttribute(MEMBER_SESSION);
	}

	/**
	 * 設定現行使用者帳號資料
	 */
	public static void setCurrentMember(HttpSession session, Member member) {
		session.setAttribute(MEMBER_SESSION, member);
	}
	
	/**
	 * 清除現行使用者帳號資料
	 */
	public static void clearCurrentMember(HttpSession session) {
		session.removeAttribute(MEMBER_SESSION);
	}
	
	/**
	 * 取得Cart session資料
	 */
	public static Cart getCartSession(HttpSession session) {
		return (Cart) session.getAttribute(CART_SESSION);
	}
	
	/**
	 * 設定Cart session資料
	 */
	public static void setCartSession(HttpSession session, Cart cart) {
		session.setAttribute(CART_SESSION, cart);
	}
	
	/**
	 * 清除Cart session資料
	 */
	public static void clearCartSession(HttpSession session) {
		session.removeAttribute(CART_SESSION);
	}
	
	/**
     * 取得source IP,避免中間有經過proxy或cdn等導致誤判source ip
     * 
     * @param request
     * @return String
     */
	public static String getRemoteIp(HttpServletRequest request) {
	    StringBuffer msg = new StringBuffer();
		String ip = request.getHeader("x-forwarded-for");
		if ( ip != null ) {
			msg.append("x-forwarded-for:"+ip);
        }
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("True-Client-IP");
            if ( ip != null ) {
            	msg.append("True-Client-IP:"+ip);
            }
        }
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if ( ip != null ) {
				msg.append("Proxy-Client-IP:"+ip);
            }
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			if ( ip != null ) {
				msg.append("WL-Proxy-Client-IP:"+ip);
            }
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if ( ip != null ) {
				msg.append("RemoteAddr:"+ip);
            }
		}
		
		if (ip!=null && ip.indexOf(",")>=0){
			ip = new StringTokenizer(ip, ",").nextToken().trim();
		}

		logger.info("getRemoteIp = "+ip+" ["+msg+"] " + request.getRequestURI());
		return ip;
	}
}
