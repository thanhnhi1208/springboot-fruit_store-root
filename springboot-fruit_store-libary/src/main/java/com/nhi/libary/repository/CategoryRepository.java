package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.dto.CategoryDto;
import com.nhi.libary.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Category findByName(String name);
	
	@Query(value = "SELECT * FROM categories WHERE activated = TRUE AND hidden = FALSE", nativeQuery = true)
	List<Category> findAllByActivated();

	@Query(value = "SELECT new com.nhi.libary.dto.CategoryDto(c.id, c.name, COUNT(p.category.id)) "
	        + "FROM Category c LEFT JOIN Product p ON c.id = p.category.id "
	        + "WHERE (c.activated = true AND c.hidden = false) AND (p IS NULL OR (p.activated = true AND p.hidden = false))  "
	        + "GROUP BY c.id, c.name")
	List<CategoryDto> getCategoryAndProduct();
}
