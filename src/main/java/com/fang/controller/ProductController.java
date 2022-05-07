package com.fang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fang.bo.ProductBO;
import com.fang.model.Category;
import com.fang.model.Product;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("product")
public class ProductController extends ControllerBase {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("productBO")
	private ProductBO productBO;
	
	@RequestMapping(value="/productList")
	public String productList() {
		return Descriptor.Page_Product_List;
	}
	
	@RequestMapping(value="/ajaxCategoryList", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String ajaxCategoryList() {
		List<Category> categoryList = productBO.getCategoryList();
		return convertToJson(categoryList);
	}
	
	@RequestMapping(value="/ajaxProductListByCategoryPage", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public String ajaxProductListByCategoryPage(HttpServletRequest request) {
		String categoryid = request.getParameter("categoryid");
		String pageNum = request.getParameter("pageNum");
		PageInfo pageInfo = productBO.getProductListByCategoryPage(categoryid, pageNum);
		return convertToJson(pageInfo);
	}
	
	@RequestMapping(value="/getProduct")
	public ModelAndView getProduct(ModelAndView modelAndView, @RequestParam("prodid") String prodid) {
		if(StringUtils.isBlank(prodid)) {
			return forward404(modelAndView);
		}
		Product product = productBO.getOnSaleProduct(prodid);
		if(product == null) {
			return forward404(modelAndView);
		}
		modelAndView.addObject(Descriptor.Obj_Product, product);
		modelAndView.setViewName(Descriptor.Page_Product);
		return modelAndView;
	}
	
	
	private static class Descriptor {
		/*
		 * 頁面路徑
		 */
		public final static String Page_Product_List = "product.list.page";
		public final static String Page_Product = "product.detail.page";
		/*
		 * 頁面物件
		 */
		public final static String Obj_Product = "product";
	}
}
