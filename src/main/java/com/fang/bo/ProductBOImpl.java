package com.fang.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fang.dao.CategoryDAO;
import com.fang.dao.ProductDAO;
import com.fang.model.BatchProduct;
import com.fang.model.Category;
import com.fang.model.Product;
import com.fang.util.BeanValidator;
import com.fang.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("productBO")
public class ProductBOImpl implements ProductBO{
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	/**
	 * 商品清單一頁要展示幾個商品
	 */
	public static final int PRODUCT_PAGESIZE = 12;
	
	/**
	 * 取得商品類別清單
	 * @return categoryList
	 */
	@Override
	public List<Category> getCategoryList(){
		List<Category> categoryList = categoryDAO.selectAllUpCategory();
		return categoryList;
	}
	
	/**
	 * 取得全部有銷售的商品清單
	 * @return productList
	 */
	@Override
	public List<Product> getAllOnSaleProductList(){
		List<Product> productList = productDAO.selectAllOnSaleProduct();
		return productList;
	}
	
	/**
	 * 取得全部有銷售的商品清單(有分頁)
	 * @param pageNum
	 * @return pageInfo
	 */
	@Override
	public PageInfo getAllOnSaleProductListPage(int pageNum) {
		PageHelper.startPage(pageNum, PRODUCT_PAGESIZE);
		List<Product> productList = productDAO.selectAllOnSaleProduct();
		PageInfo pageInfo = new PageInfo<>(productList);
		return pageInfo;
	}
	
	/**
	 * 以商品類別取得商品清單
	 * @param categoryid
	 * @return productList
	 */
	@Override
	public List<Product> getProductListByCategory(String categoryid){
		int id = 0;
		if(StringUtils.isNotBlank(categoryid)) {
			id = Integer.parseInt(categoryid);
		}
		List<Product> productList = productDAO.selectProductByCategoryid(id);
		return productList;
	}
	
	/**
	 * 以商品類別取得商品清單(有分頁)
	 * @param categoryid, strPageNum
	 * @return pageInfo
	 */
	@Override
	public PageInfo getProductListByCategoryPage(String categoryid, String strPageNum){
		int id = (StringUtils.isNotBlank(categoryid) ? Integer.parseInt(categoryid) : 0);
		int pageNum = (StringUtils.isNotBlank(strPageNum) ? Integer.parseInt(strPageNum) : 1);
		PageHelper.startPage(pageNum, PRODUCT_PAGESIZE);
		List<Product> productList = productDAO.selectProductByCategoryid(id);
		PageInfo pageInfo = new PageInfo<>(productList);
		return pageInfo;
	}
	
	/**
	 * 以商品編號取得銷售中商品
	 * @param prodid(String)
	 * @return product
	 */
	@Override
	public Product getOnSaleProduct(String prodid) {
		int id = 0;
		if(StringUtils.isBlank(prodid) || !StringUtils.isNumeric(prodid)) {
			return null;
		}else {
			id = Integer.parseInt(prodid);
		} 
		Product product = productDAO.selectOnSaleProductByProdid(id);
		return product;
	}
	
	/**
	 * 以商品編號取得銷售中商品
	 * @param prodid(int)
	 * @return product
	 */
	@Override
	public Product getOnSaleProduct(int prodid) {
		Product product = productDAO.selectOnSaleProductByProdid(prodid);
		return product;
	}
	
	/**
	 * 取得全部商品清單(有分頁)
	 * @param pageNum
	 * @return pageInfo
	 */
	public PageInfo getAllProductListPage(int pageNum) {
		PageHelper.startPage(pageNum, PRODUCT_PAGESIZE);
		List<Product> productList = productDAO.selectAllProduct();
		PageInfo pageInfo = new PageInfo<>(productList);
		return pageInfo;
	}
	
	/**
	 * 以商品編號取得商品
	 * @param prodid
	 * @return product
	 */
	public Product getProduct(int prodid) {
		Product product = productDAO.selectProductByProdid(prodid);
		return product;
	}
	
	/**
	 * 檢查商品
	 * @param product
	 * @return Map<String, String>
	 */
	public Map<String, String> checkProductFields(Product product){
		Map<String, String> errorMap = new HashMap<>();
		if(StringUtils.isBlank(product.getBegin_date()) || StringUtils.isBlank(product.getBegin_time())) {
			errorMap.put("begin_date", "商品上架開始日期時間不能為空");
		}
		if(StringUtils.isBlank(product.getEnd_date()) || StringUtils.isBlank(product.getEnd_time())) {
			errorMap.put("end_date", "商品上架結束日期時間不能為空");
		}
		Timestamp beginDate = null;
		Timestamp endDate = null;
		try {
			beginDate = StringUtil.convertStringToTimestamp(product.getBegin_date(), product.getBegin_time());
		}catch(Exception e) {
			errorMap.put("begin_date", "商品上架開始時間格式錯誤");
		}
		try {
			endDate = StringUtil.convertStringToTimestamp(product.getEnd_date(), product.getEnd_time());
		}catch(Exception e) {
			errorMap.put("end_date", "商品上架結束時間格式錯誤");
		}
		if(endDate != null) {
			if(endDate.before(new StringUtil().getCurrentTimestamp())) {
				errorMap.put("end_date", "商品上架結束時間必須是未來的時間");
			}
			if(beginDate != null) {
				if(endDate.before(beginDate)) {
					errorMap.put("end_date", "商品上架結束時間必須晚於商品上架開始時間");
				}
			}
		}
		product.setBegindate(beginDate);
		product.setEnddate(endDate);
		//UI編輯斷行為\r\n，轉為<br>才可以在jsp顯示
		if(StringUtils.isNotBlank(product.getDescription())) {
			product.setDescription(product.getDescription().replaceAll("\r\n", "<br>"));
		}
		Category category = this.getCategory(product.getCategoryid());
		if(category == null) {
			errorMap.put("categoryid", "無此商品類別或已下架");
		}
		return errorMap;
	}
	
	/**
	 * 編輯商品
	 * @param product
	 */
	public void updateProduct(Product product) {
		product.setUpdatetime(new StringUtil().getCurrentTimestamp());
		productDAO.updateProductByPrimaryKey(product);
	}
	
	/**
	 * 新增商品
	 * @param product
	 */
	public void insertProduct(Product product) {
		product.setCreatetime(new StringUtil().getCurrentTimestamp());
		productDAO.insertProduct(product);
	}
	
	/**
	 * 取得商品類別
	 * @return category
	 */
	public Category getCategory(int categoryid) {
		return categoryDAO.selectCategory(categoryid);
	}
	
	/**
	 * 檢查excel商品清單
	 * @param products
	 * @return hasError
	 */
	public boolean checkBatchProductFields(List<BatchProduct> products){
		boolean hasError = false;
		if(products != null && products.size() > 0) {
			for(BatchProduct batchProduct : products) {
				Map<String, String> errorMap = BeanValidator.validate(batchProduct);
				Timestamp beginDate = null;
				Timestamp endDate = null;
				if(StringUtils.isNotBlank(batchProduct.getBegindate())) {
					try {
						beginDate = StringUtil.convertStringToTimestamp(batchProduct.getBegindate());
					}catch(Exception e) {
						errorMap.put("begindate", "商品上架開始時間格式錯誤");
					}
				}
				if(StringUtils.isNotBlank(batchProduct.getEnddate())) {
					try {
						endDate = StringUtil.convertStringToTimestamp(batchProduct.getEnddate());
					}catch(Exception e) {
						errorMap.put("enddate", "商品上架結束時間格式錯誤");
					}
				}
				if(endDate != null) {
					if(endDate.before(new StringUtil().getCurrentTimestamp())) {
						errorMap.put("enddate", "商品上架結束時間必須是未來的時間");
					}
					if(beginDate != null) {
						if(endDate.before(beginDate)) {
							errorMap.put("enddate", "商品上架結束時間必須晚於上架開始時間");
						}
					}
				}
				if(batchProduct.getCategoryid() != null) {
					Category category = this.getCategory(batchProduct.getCategoryid());
					if(category == null) {
						errorMap.put("categoryid", "無此商品類別或已下架");
					}
				}
				batchProduct.setErrorMap(errorMap);
				if(errorMap != null && errorMap.size() > 0) {
					hasError = true;
				}
			}
		}
		return hasError;
	}
	
	/**
	 * excel匯入商品轉為product
	 * @param batchProduct
	 * @return Product
	 */
	public Product convertBatchToProduct(BatchProduct batchProduct){
		Product product = new Product();
		product.setName(batchProduct.getName());
		product.setPrice(batchProduct.getPrice());
		//Excel編輯斷行為\n，轉為<br>才可以在jsp顯示
		if(StringUtils.isNotBlank(batchProduct.getDescription())) {
			product.setDescription(batchProduct.getDescription().replaceAll("\n", "<br>"));
		}
		Timestamp beginDate = StringUtil.convertStringToTimestamp(batchProduct.getBegindate());
		product.setBegindate(beginDate);
		Timestamp endDate = StringUtil.convertStringToTimestamp(batchProduct.getEnddate());
		product.setEnddate(endDate);
		product.setStock(batchProduct.getStock());
		product.setCategoryid(batchProduct.getCategoryid());
		product.setPicurl(batchProduct.getPicurl());
		return product;
	}
	
	/**
	 * 儲存excel匯入商品
	 * @param batchProducts
	 * @return successNum
	 */
	public int saveBatchProduct(List<BatchProduct> batchProducts) {
		int successNum = 0;
		if(batchProducts != null) {
			for(BatchProduct batchProduct : batchProducts) {
				try {
					Product product = this.convertBatchToProduct(batchProduct);
					this.insertProduct(product);
					successNum++;
				}catch(Exception e) {
					logger.warn("儲存excel匯入商品 失敗 batchProduct="+batchProduct.toString(), e);
				}
			}
		}
		return successNum;
	}
}
