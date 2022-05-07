package com.fang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fang.model.Product;

public interface ProductDAO {
	
	public List<Product> selectAllProduct();
	
	public List<Product> selectAllOnSaleProduct();
	
	public List<Product> selectProductByCategoryid(int categoryid);
	
	public Product selectProductByProdid(int prodid);
	
	public Product selectOnSaleProductByProdid(int prodid);
	
	public void insertProduct(Product product);
	
	public int deductProductStock(@Param("prodid")int prodid, @Param("qty")int qty);
	
	public void updateProductByPrimaryKey(Product product);

}
