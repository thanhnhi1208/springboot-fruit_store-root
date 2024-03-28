package com.nhi.libary.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.dto.CategoryDto;
import com.nhi.libary.model.Category;
import com.nhi.libary.repository.CategoryRepository;
import com.nhi.libary.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		try {
			return this.categoryRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Category findById(Long id) {
		try {
			return this.categoryRepository.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Category save(Category category) {
		try {
			Category temp = this.categoryRepository.findByName(category.getName());
			if(temp != null && category.getId() != temp.getId()) {
				return null;
			}else {
				category.setActivated(true);
				category.setHidden(false);
				return this.categoryRepository.save(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Category update(Category category) {
		try {
			Category temp = this.categoryRepository.findByName(category.getName());
			if(temp != null && category.getId() != temp.getId()) {
				return null;
			}else {
				return this.categoryRepository.save(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void activatedById(Long id) {
		try {
			Category category = this.categoryRepository.findById(id).get();
			category.setActivated(true);
			category.setHidden(false);
			this.categoryRepository.save(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void hiddenById(Long id) {
		try {
			Category category = this.categoryRepository.findById(id).get();
			category.setActivated(false);
			category.setHidden(true);
			this.categoryRepository.save(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			this.categoryRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Category> findAllByActivated() {
		try {
			return this.categoryRepository.findAllByActivated();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CategoryDto> getCategoryAndProduct() {
		return this.categoryRepository.getCategoryAndProduct();
	}

}
