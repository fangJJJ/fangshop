package com.fang.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fang.bo.ProductBO;
import com.fang.model.BatchProduct;
import com.fang.model.Category;
import com.fang.model.Product;
import com.fang.util.ExcelToPojoUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("admin")
public class AdminController {
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("productBO")
	private ProductBO productBO;
	
	@RequestMapping(value="/goProductList")
	public ModelAndView goProductList(@RequestParam(value="pageNum", required=false, defaultValue="1") Integer pageNum) {
		return productListModelAndView(pageNum);
	}
	
	@RequestMapping(value="/goEditProductPage")
	public ModelAndView goEditProductPage(@RequestParam("prodid") Integer prodid) {
		Product product = productBO.getProduct(prodid);
		return productModelAndView(product, Descriptor.Page_Admin_Product_Edit);
	}
	
	@RequestMapping(value="/goNewProductPage")
	public ModelAndView goNewProductPage() {
		return productModelAndView(null, Descriptor.Page_Admin_Product_New);
	}
	
	@RequestMapping(value="/goProductPage")
	public ModelAndView goProductPage(@RequestParam("prodid") Integer prodid) {
		Product product = productBO.getProduct(prodid);
		return productModelAndView(product, Descriptor.Page_Admin_Product_Detail);
	}
	
	@RequestMapping(value="/editProduct", method = RequestMethod.POST)
	public ModelAndView editProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
		if(product == null || product.getProdid() == 0) {
			FieldError error = new FieldError("product", "name", "商品操作錯誤，請重新操作");
		    bindingResult.addError(error);
		    return productModelAndView(product, Descriptor.Page_Admin_Product_Edit);
		}
		Product dbProduct = productBO.getProduct(product.getProdid());
		if(dbProduct == null) {
			FieldError error = new FieldError("product", "name", "商品操作錯誤，請重新操作");
		    bindingResult.addError(error);
		    return productModelAndView(product, Descriptor.Page_Admin_Product_Edit);
		}
		if(bindingResult.hasErrors()) {
			return productModelAndView(product, Descriptor.Page_Admin_Product_Edit);
		}
		Map<String, String> errorMap = productBO.checkProductFields(product);
		if(!errorMap.isEmpty()) {
			for (Map.Entry<String, String> entry : errorMap.entrySet()) {
			    FieldError error = new FieldError("product", entry.getKey(), entry.getValue());
			    bindingResult.addError(error);
			}
			return productModelAndView(product, Descriptor.Page_Admin_Product_Edit);
		}
		try {
			productBO.updateProduct(product);
		}catch(Exception e) {
			logger.error("updateProduct fail", e);
			FieldError error = new FieldError("product", "name", "編輯商品失敗，請重新操作");
		    bindingResult.addError(error);
		    return productModelAndView(product, Descriptor.Page_Admin_Product_Edit);
		}
		return productListModelAndView(1);
	}
	
	@RequestMapping(value="/newProduct", method = RequestMethod.POST)
	public ModelAndView newProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
		if(product == null) {
			FieldError error = new FieldError("product", "name", "商品操作錯誤，請重新操作");
		    bindingResult.addError(error);
		    return productModelAndView(product, Descriptor.Page_Admin_Product_New);
		}
		if(bindingResult.hasErrors()) {
			return productModelAndView(product, Descriptor.Page_Admin_Product_New);
		}
		Map<String, String> errorMap = productBO.checkProductFields(product);
		if(!errorMap.isEmpty()) {
			for (Map.Entry<String, String> entry : errorMap.entrySet()) {
			    FieldError error = new FieldError("product", entry.getKey(), entry.getValue());
			    bindingResult.addError(error);
			}
			return productModelAndView(product, Descriptor.Page_Admin_Product_New);
		}
		try {
			productBO.insertProduct(product);
		}catch(Exception e) {
			logger.error("insertProduct fail", e);
			FieldError error = new FieldError("product", "name", "新增商品失敗，請重新操作");
		    bindingResult.addError(error);
		    return productModelAndView(product, Descriptor.Page_Admin_Product_New);
		}
		return productListModelAndView(1);
	}
	
	@RequestMapping(value="/goUploadExcelPage")
	public String goUploadExcelPage(HttpSession session) {
		return Descriptor.Page_Admin_Product_Upload_Excel;
	}
	
	@RequestMapping(value="/uploadExcel", method = RequestMethod.POST)
	public ModelAndView uploadExcel(ModelAndView modelAndView, @RequestParam("file") MultipartFile file, HttpSession session) {
		if(file == null || file.isEmpty()) {
			modelAndView.addObject("MESSAGE_ERROR", "上傳檔案失敗");
			modelAndView.setViewName(Descriptor.Page_Admin_Product_Upload_Excel);
			return modelAndView;
		}
		String fileName = file.getOriginalFilename();
		List<BatchProduct> products = new ArrayList<>();
		if(fileName.endsWith("xls") || fileName.endsWith("xlsx")){
    		try {
    			InputStream is = file.getInputStream();
    			products = ExcelToPojoUtil.toPojo(BatchProduct.class, is);
    		}catch(Exception e) {
    			modelAndView.addObject("MESSAGE_ERROR", "上傳檔案失敗");
    			modelAndView.setViewName(Descriptor.Page_Admin_Product_Upload_Excel);
    			return modelAndView;
    		}
    	}else {
    		modelAndView.addObject("MESSAGE_ERROR", fileName + "不是excel檔案");
			modelAndView.setViewName(Descriptor.Page_Admin_Product_Upload_Excel);
			return modelAndView;
    	}
		if(products == null || products.size() == 0) {
			modelAndView.addObject("MESSAGE_ERROR", "檔案內容無商品資料");
			modelAndView.setViewName(Descriptor.Page_Admin_Product_Upload_Excel);
			return modelAndView;
		}
		boolean hasError = productBO.checkBatchProductFields(products);
		if(!hasError) {
			//如果商品檢核都沒錯，就將excel資料塞入session
			session.setAttribute(Descriptor.BATCHPRODUCTLIST_SESSION, products);
		}
		modelAndView.addObject("hasError", hasError);
		modelAndView.addObject(Descriptor.Obj_BatchProductList, products);
		modelAndView.setViewName(Descriptor.Page_Admin_Product_Show_Excel);
		return modelAndView;
	}
	
	@RequestMapping(value="/saveExcelProduct")
	public ModelAndView saveExcelProduct(ModelAndView modelAndView, HttpSession session) {
		if(session == null || session.getAttribute(Descriptor.BATCHPRODUCTLIST_SESSION) == null) {
			modelAndView.addObject("MESSAGE_ERROR", "上傳檔案失敗");
			modelAndView.setViewName(Descriptor.Page_Admin_Product_Upload_Excel);
			return modelAndView;
		}
		List<BatchProduct> batchProducts = (List<BatchProduct>) session.getAttribute(Descriptor.BATCHPRODUCTLIST_SESSION);
		if(batchProducts == null || batchProducts.size() == 0) {
			modelAndView.addObject("MESSAGE_ERROR", "上傳檔案失敗");
			modelAndView.setViewName(Descriptor.Page_Admin_Product_Upload_Excel);
			return modelAndView;
		}
		String message = "";
		try {	
			int totalNum = batchProducts.size();
			logger.info("Excel匯入商品開始 總筆數:"+totalNum);
			int successNum = productBO.saveBatchProduct(batchProducts);
			logger.info("Excel匯入商品結束");
			message = "儲存完成，共 成功 "+successNum+" 筆，失敗 "+(totalNum-successNum)+" 筆";
		}catch(Exception e) {
			logger.error("saveBatchProduct fail", e);
			message = "儲存失敗，請重新操作。";
		}
		session.removeAttribute(Descriptor.BATCHPRODUCTLIST_SESSION);
		modelAndView.addObject("info", message);
		modelAndView.setViewName(Descriptor.Page_Admin_Product_Finish_Excel);
		return modelAndView;
	}
	
	@RequestMapping(value="/downloadExcel")
	public void downloadExcel(HttpServletResponse response) {
		try {
			response.setHeader("Content-Disposition", "attachment;filename=newProductList.xlsx");
			InputStream is = this.getClass().getResourceAsStream("/newProductList.xlsx");
			OutputStream output = response.getOutputStream();
			byte[] buffer = new byte[10240];
			int length;
			while((length = is.read(buffer)) != -1) {
				output.write(buffer, 0, length);
				output.flush();
			}
			is.close();
			output.close();
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("downloadExcel fail", e);
		}
	}
	
	private ModelAndView productModelAndView(Product product, String view) {
		List<Category> categoryList = productBO.getCategoryList();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(Descriptor.Obj_Product, product);
		modelAndView.addObject(Descriptor.Obj_CategoryList, categoryList);
		modelAndView.setViewName(view);
		return modelAndView;
	}
	
	private ModelAndView productListModelAndView(int pageNum) {
		PageInfo pageInfo = productBO.getAllProductListPage(pageNum);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(Descriptor.Obj_ProductList, pageInfo.getList());
		modelAndView.addObject(Descriptor.Obj_Pages, pageInfo.getPages());
		modelAndView.setViewName(Descriptor.Page_Admin_Product_List);
		return modelAndView;
	}
	
	private static class Descriptor {
		/*
		 * 頁面路徑
		 */
		public final static String Page_Admin_Product_List = "admin.product.list.page";
		public final static String Page_Admin_Product_Edit = "admin.product.edit.page";
		public final static String Page_Admin_Product_Detail = "admin.product.detail.page";
		public final static String Page_Admin_Product_New = "admin.product.new.page";
		public final static String Page_Admin_Product_Upload_Excel = "admin.product.upload.excel.page";
		public final static String Page_Admin_Product_Show_Excel = "admin.product.show.excel.page";
		public final static String Page_Admin_Product_Finish_Excel = "admin.product.finish.excel.page";
		/*
		 * 頁面物件
		 */
		public final static String Obj_ProductList = "productList";
		public final static String Obj_Pages = "pages";
		public final static String Obj_Product = "product";
		public final static String Obj_CategoryList = "categoryList";
		public final static String Obj_BatchProductList = "batchProductList";
		private static final String BATCHPRODUCTLIST_SESSION = "batchProductList_session";
	}
}
