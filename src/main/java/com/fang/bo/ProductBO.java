package com.fang.bo;

import java.util.List;
import java.util.Map;

import com.fang.model.BatchProduct;
import com.fang.model.Category;
import com.fang.model.Product;
import com.github.pagehelper.PageInfo;

public interface ProductBO {
	
	/**
	 * 取得商品類別清單
	 * @return categoryList
	 */
	public List<Category> getCategoryList();
	
	/**
	 * 取得全部有銷售的商品清單
	 * @return productList
	 */
	public List<Product> getAllOnSaleProductList();
	
	/**
	 * 取得全部有銷售的商品清單(有分頁)
	 * @param pageNum
	 * @return pageInfo
	 */
	public PageInfo getAllOnSaleProductListPage(int pageNum);
	
	/**
	 * 以商品類別取得商品清單
	 * @return productList
	 */
	public List<Product> getProductListByCategory(String categoryid);
	
	/**
	 * 以商品類別取得商品清單(有分頁)
	 * @return pageInfo
	 */
	public PageInfo getProductListByCategoryPage(String categoryid, String strPageNum);
	
	/**
	 * 以商品編號取得銷售中商品
	 * @param prodid(String)
	 * @return product
	 */
	public Product getOnSaleProduct(String prodid);
	
	/**
	 * 以商品編號取得銷售中商品
	 * @param prodid(int)
	 * @return product
	 */
	public Product getOnSaleProduct(int prodid);
	
	/**
	 * 取得全部商品清單(有分頁)
	 * @param pageNum
	 * @return pageInfo
	 */
	public PageInfo getAllProductListPage(int pageNum);
	
	/**
	 * 以商品編號取得商品
	 * @param prodid
	 * @return product
	 */
	public Product getProduct(int prodid);
	
	/**
	 * 檢查商品
	 * @param product
	 * @return Map<String, String>
	 */
	public Map<String, String> checkProductFields(Product product);
	
	/**
	 * 編輯商品
	 * @param product
	 */
	public void updateProduct(Product product);
	
	/**
	 * 新增商品
	 * @param product
	 */
	public void insertProduct(Product product);
	
	/**
	 * 取得商品類別
	 * @return category
	 */
	public Category getCategory(int categoryid);
	
	/**
	 * 檢查excel商品清單
	 * @param products
	 * @return hasError
	 */
	public boolean checkBatchProductFields(List<BatchProduct> products);
	
	/**
	 * 儲存excel匯入商品
	 * @param batchProducts
	 * @return successNum
	 */
	public int saveBatchProduct(List<BatchProduct> batchProducts);
}
