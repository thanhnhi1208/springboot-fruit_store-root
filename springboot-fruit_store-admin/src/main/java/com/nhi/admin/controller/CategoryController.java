package com.nhi.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhi.libary.model.Admin;
import com.nhi.libary.model.Category;
import com.nhi.libary.service.AdminService;
import com.nhi.libary.service.CategoryService;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService; 
	
	@Autowired 
	private AdminService adminService;

	@GetMapping("/categories")
	public String categories(Model m, Authentication authentication) {
		Admin admin = adminService.findByUsername(authentication.getName());
		m.addAttribute("admin", admin);
		m.addAttribute("title", "CATEGORY");
		m.addAttribute("categoryList", this.categoryService.findAll());
		return "categories";
	}
	
	@PostMapping("/categories/insert")
	public String insertCategory(Category category, RedirectAttributes redirectAttributes) {
		try {
			if(this.categoryService.save(category) != null) {
				redirectAttributes.addFlashAttribute("success", "Insert Successfully" );
			}else {
				redirectAttributes.addFlashAttribute("failed", "Duplicate name !" );
			}
			
			return "redirect:/categories";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Insert failed" );
			return "redirect:/categories";
		}
	}
	
	@GetMapping("/categories/findById")
	@ResponseBody
	public Category findById(Long id) {
		return this.categoryService.findById(id);
	}
	
	@RequestMapping(value = "/categories/update", method = {RequestMethod.GET, RequestMethod.PUT})
	public String updateCategory(Category category, RedirectAttributes redirectAttributes) {
		try {
			if(this.categoryService.update(category) != null) {
				redirectAttributes.addFlashAttribute("success", "Update Successfully" );
			}else {
				redirectAttributes.addFlashAttribute("failed", "Duplicate name !" );
			}
			
			return "redirect:/categories";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Update failed" );
			return "redirect:/categories";
		}
	}
	
	
	@RequestMapping(value = "/categories/deleteById", method = {RequestMethod.GET, RequestMethod.DELETE})
	public String deleteCategory(Long id, RedirectAttributes redirectAttributes) {
		try {
			this.categoryService.deleteById(id);
			redirectAttributes.addFlashAttribute("success", "Delete Successfully" );
			return "redirect:/categories";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Delete failed" );
			return "redirect:/categories";
		}
	}
	
	@GetMapping(value = "/categories/activatedById")
	public String activatedCategory(Long id, RedirectAttributes redirectAttributes) {
		try {
			this.categoryService.activatedById(id);
			redirectAttributes.addFlashAttribute("success", "Activated Successfully" );
			return "redirect:/categories";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Activated failed" );
			return "redirect:/categories";
		}
	}
	
	@GetMapping(value = "/categories/hiddenById")
	public String hiddenById(Long id, RedirectAttributes redirectAttributes) {
		try {
			this.categoryService.hiddenById(id);
			redirectAttributes.addFlashAttribute("success", "Hidden Successfully" );
			return "redirect:/categories";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Hidden failed" );
			return "redirect:/categories";
		}
	}
}
