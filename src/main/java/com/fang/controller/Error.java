package com.fang.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;



@Controller
public class Error extends ControllerBase {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@RequestMapping("Error")
	public String error(HttpServletRequest request) {
		logger.debug("Controller error");
		
		return VIEW_ERROR_PAGE;
	}
	
	
	/***************************************************************
	 ** Tiles
	 **************************************************************/
	private static String VIEW_ERROR_PAGE = "error.base.page";

}
