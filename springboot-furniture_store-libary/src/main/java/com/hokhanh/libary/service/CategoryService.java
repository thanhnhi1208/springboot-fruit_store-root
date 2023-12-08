package com.hokhanh.libary.service;

import java.util.List;

import com.hokhanh.libary.dto.CategoryDto;
import com.hokhanh.libary.model.Category;

public interface CategoryService {

	List<Category> findAll();
	
	Category findById(Long id);
	
	Category save(Category category);
	
	Category update(Category category);
	
	void activatedById(Long id);
	
	void hiddenById(Long id);
	
	void deleteById(Long id);
	
	List<Category> findAllByActivated();
	
	List<CategoryDto> getCategoryAndProduct();
	
	
}
