package com.fang.service.impl;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fang.model.Member;
import com.fang.service.SocialService;
import com.fang.util.CommonUtil;
import com.google.gson.Gson;

public class FacebookImpl implements SocialService{
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Override
	public Member getSocialMemberInfo(HttpServletRequest request) throws Exception {
		String subLog = " (ip="+CommonUtil.getRemoteIp(request)+")";
		String token = request.getParameter("token");
		if(StringUtils.isBlank(token)) {
			throw new Exception("FACEBOOK登入的token為null"+subLog);
		}
		// 用token取得facebook帳號資訊
		String postUrl = "https://graph.facebook.com/v13.0/me?access_token="+token;
		String json = "{\"fields\":\"name,email\"}";
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.setEntity(entity);		
		String responseMessage = post(httpPost, subLog);
		Member member = new Gson().fromJson(responseMessage, Member.class);
		
		return member;
	}
	
	private String post(HttpPost httpPost, String subLog) throws Exception {
		String responseMessage = "";
		CloseableHttpClient client = HttpClients.createDefault();
		try{
			RequestConfig defaultRequestConfig = RequestConfig.custom()
					.setSocketTimeout(5000)  
					.setConnectTimeout(5000)  
					.setConnectionRequestTimeout(5000)  
					.build();
			httpPost.setConfig(defaultRequestConfig);
			CloseableHttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK){
				responseMessage = EntityUtils.toString(response.getEntity());
			}else{
				logger.warn("statusCode:"+statusCode + ", responseMessage:"+response.getEntity()+subLog);
				throw new Exception("取得FACEBOOK授權資訊失敗");
			}
		}finally{
			// 無論如何都必須釋放連接
			client.close();
		}
		return responseMessage;
	}

}
