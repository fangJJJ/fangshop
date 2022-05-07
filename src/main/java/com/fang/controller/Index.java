package com.fang.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@RequestMapping("Index")
	public ModelAndView index() {
		logger.debug("Controller index");
		return new ModelAndView(VIEW_INDEX_BASE_PAGE);
	}
	
	/***************************************************************
	 ** Tiles
	 **************************************************************/
	private static String VIEW_INDEX_BASE_PAGE = "index.page";

}
