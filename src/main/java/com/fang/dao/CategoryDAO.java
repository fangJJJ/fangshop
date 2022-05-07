package com.fang.dao;

import java.util.List;

import com.fang.model.Category;

public interface CategoryDAO {
	
	public List<Category> selectAllUpCategory();
	
	public Category selectCategory(int categoryid);

}
