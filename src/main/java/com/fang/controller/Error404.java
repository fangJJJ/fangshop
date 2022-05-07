package com.fang.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;



@Controller
public class Error404 extends ControllerBase {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@RequestMapping("Error404")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String error404(HttpServletRequest request) {
		logger.debug("Controller error404");
		
		return VIEW_NOT_FOUND_PAGE;
	}
	
	
	/***************************************************************
	 ** Tiles
	 **************************************************************/
	private static String VIEW_NOT_FOUND_PAGE = "not.found.page";

}
