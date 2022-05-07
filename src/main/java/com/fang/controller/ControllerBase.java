package com.fang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

public class ControllerBase {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	/*
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        logger.error("handleException", ex);
        return forward404();
    }
    */
	
	/**
     * forward error頁面
     */
    public String forwardError() {
    	return "forward:/Error.do";
    }
    
    /**
     * forward error頁面
     */
    public ModelAndView forwardError(ModelAndView modelAndView) {
    	modelAndView.setViewName(forwardError());
    	return modelAndView;
    }
    
    /**
     * forward 404頁面
     */
    public String forward404() {
    	return "forward:/Error404.do";
    }
    
    /**
     * forward 404頁面
     */
    public ModelAndView forward404(ModelAndView modelAndView) {
    	modelAndView.setViewName(forward404());
    	return modelAndView;
    }
    
    /**
     * forward 首頁
     */
    public String forwardIndex() {
    	return "forward:/Index.do";
    }
    
    /**
     * forward 首頁
     */
    public ModelAndView forwardIndex(ModelAndView modelAndView) {
    	modelAndView.setViewName(forwardIndex());
    	return modelAndView;
    }
    
    /**
     * 	轉出JSON資料(Object->Json)
     *  key為result
     * @param obj
     * @return String
     */
    public String convertToJson(Object obj) {
    	Map map = new HashMap();
    	map.put("result", obj);
    	return forwardJson(map); 
    }
    
    /**
     * 	轉出JSON資料(Map->Json)
     *  自訂key
     * @param map
     * @return String
     */
    public String forwardJson(Map map) {
    	return new Gson().toJson(map);
    }
    
    
    // 警示訊息
    public static String MESSAGE_INFO = "MESSAGE_INFO";
    public static String MESSAGE_ERROR = "MESSAGE_ERROR";
    public static String MESSAGE_FATAL = "MESSAGE_FATAL";
    
    public static String VIEW_JSON_PAGE = "json.empty.page";
    public static String JSON_ATTRIBUTE = "JSON_ATTRIBUTE";
}
