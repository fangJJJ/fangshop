package com.fang.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fang.constants.SocialSour;
import com.fang.service.SocialService;

public class SocialFactory implements ApplicationContextAware {
	
	private static final Logger logger = LogManager.getLogger(SocialFactory.class);
	
	private static ApplicationContext context;
	
	/**
	 * 取得Bean
	 * @param socialSour
	 * @return socialService
	 * @throws Exception
	 */
	public static SocialService getSocialBean(SocialSour socialSour) throws Exception{
		if(socialSour == null) {
			logger.error("[getSocialBean] socialSour is null.");
			throw new Exception("[getSocialBean] socialSour is null.");
		}
		SocialService socialService = (SocialService)context.getBean(socialSour.getBean());
		return socialService;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;
	}
}
