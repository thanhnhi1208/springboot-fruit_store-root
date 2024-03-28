package com.nhi.admin.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhi.libary.dto.ProductDto;
import com.nhi.libary.model.Admin;
import com.nhi.libary.model.Product;
import com.nhi.libary.repository.CategoryRepository;
import com.nhi.libary.service.AdminService;
import com.nhi.libary.service.CategoryService;
import com.nhi.libary.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AdminService adminService;

	@GetMapping("/products")
	public String products(Model m, Authentication authentication) {
		Admin admin = adminService.findByUsername(authentication.getName());
		m.addAttribute("admin", admin);
		m.addAttribute("title", "PRODUCT");
		m.addAttribute("productList", productService.findAll());
		m.addAttribute("categoryList", categoryService.findAllByActivated());
		return "products";
	}

	@GetMapping("/products/{pageNo}")
	public String productsPage(@PathVariable("pageNo") int pageNo, Model m, Authentication authentication) {
		Admin admin = adminService.findByUsername(authentication.getName());
		m.addAttribute("admin", admin);
		
		Page<Product> productList = this.productService.productPage(pageNo);
		
		String insert = (String) m.asMap().get("insert");
		
		if(insert != null) {
			if(!this.productService.productPage(productList.getTotalPages()).isEmpty()) {
				return "redirect:/products/"+productList.getTotalPages();
			}
		}
		
		if(productList.isEmpty() && pageNo != 1) {
			productList = this.productService.productPage(pageNo-1);
			return "redirect:/products/"+(pageNo -1);
		}

		m.addAttribute("title", "PRODUCT");
		m.addAttribute("totalPage", productList.getTotalPages());
		m.addAttribute("currentPage", pageNo);

		m.addAttribute("productList", productList);
		m.addAttribute("categoryList", categoryService.findAllByActivated());
		return "products";
	}

	@PostMapping("/products/insert")
	public String insertProduct(ProductDto productDto, @RequestParam("imageProduct") MultipartFile imageProduct,
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "totalPage", defaultValue = "1") int totalPage) {

		if(totalPage ==0 ) {
			totalPage = 1;
		}
		
		Product product = this.productService.save(productDto, imageProduct);
		try {
			if (product != null) {
				redirectAttributes.addFlashAttribute("success", "Insert Successfully");
				redirectAttributes.addFlashAttribute("insert", "Insert Successfully");
			} else {
				redirectAttributes.addFlashAttribute("failed", "Insert Failed because Duplicate ");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("failed", "Insert Failed because Duplicate ");
		}
		return "redirect:/products/" + totalPage;

	}

	@GetMapping("products/getById")
	@ResponseBody
	public Product getById(Long id) {
		return this.productService.findById(id);
	}

	@PostMapping(value = "/products/update")
	public String updateProduct(ProductDto productDto, @RequestParam("imageProduct") MultipartFile imageProduct,
			RedirectAttributes redirectAttributes, int currentPage) {
		Product product = this.productService.update(productDto, imageProduct);
		try {
			if (product != null) {
				redirectAttributes.addFlashAttribute("success", "Update Successfully");
			} else {
				redirectAttributes.addFlashAttribute("failed", "Update Failed because Duplicate ");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("failed", "Update Failed");
		}
		return "redirect:/products/" + currentPage;
	}

	@GetMapping("/products/hiddenById")
	public String hiddenById(Long id, RedirectAttributes redirectAttributes, int currentPage) {
		try {
			this.productService.hiddenById(id);
			redirectAttributes.addFlashAttribute("success", "Hidden Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Hidden Failed");
		}
		return "redirect:/products/" + currentPage;
	}

	@GetMapping("/products/activatedById")
	public String activateProduct(Long id, RedirectAttributes redirectAttributes, int currentPage) {
		try {
			this.productService.activatedById(id);
			redirectAttributes.addFlashAttribute("success", "Activated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Activated Failed");
		}
		return "redirect:/products/" + currentPage;
	}

	@GetMapping("/products/deleteById")
	public String deleteProduct(Long id, RedirectAttributes redirectAttributes, int currentPage) {
		try {
			this.productService.deleteById(id);
			redirectAttributes.addFlashAttribute("success", "Delete Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("failed", "Delete Failed");
		}
		return "redirect:/products/" + currentPage;
	}

	@GetMapping("/products/search")
	public String searchProducts(Model m, String keyword, @RequestParam(name = "pageNo") int pageNo, Authentication authentication) {
		try {
			Admin admin = adminService.findByUsername(authentication.getName());
			m.addAttribute("admin", admin);
			if (keyword == "") {
				return "redirect:/products/1";
			} else {
				Page<Product> productList = this.productService.searchProducts(keyword, pageNo);
				m.addAttribute("productList", productList);
				m.addAttribute("keyword", keyword);

				m.addAttribute("title", "PRODUCT");
				m.addAttribute("totalPage", productList.getTotalPages());

//		trang bắt đầu là 0
				m.addAttribute("currentPage", productList.getNumber() + 1);
				m.addAttribute("categoryList", categoryService.findAllByActivated());

				m.addAttribute("searchProduct", "search Product");
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("failed", "Search Failed");
		}
		return "products";
	}

	@GetMapping("/products/search/hiddenById")
	public String hiddenSearchProduct(Long id, Model m, String keyword, @RequestParam(name = "pageNo") int pageNo, Authentication authentication) {
		try {
			Admin admin = adminService.findByUsername(authentication.getName());
			m.addAttribute("admin", admin);
			Page<Product> productList = this.productService.searchProducts(keyword, pageNo);
			this.productService.hiddenById(id);

			m.addAttribute("productList", productList);
			m.addAttribute("keyword", keyword);

			m.addAttribute("title", "PRODUCT");
			m.addAttribute("totalPage", productList.getTotalPages());

//	trang bắt đầu là 0
			m.addAttribute("currentPage", productList.getNumber() + 1);
			m.addAttribute("categoryList", categoryService.findAllByActivated());

			m.addAttribute("searchProduct", "search Product");
			m.addAttribute("success", "Hidden Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("failed", "Hidden Failed");
		}
		return "products";
	}

	@GetMapping("/products/search/activatedById")
	public String activatedSearchProduct(Long id, Model m, String keyword, @RequestParam(name = "pageNo") int pageNo, Authentication authentication) {
		try {
			Admin admin = adminService.findByUsername(authentication.getName());
			m.addAttribute("admin", admin);
			Page<Product> productList = this.productService.searchProducts(keyword, pageNo);
			this.productService.activatedById(id);

			m.addAttribute("productList", productList);
			m.addAttribute("keyword", keyword);

			m.addAttribute("title", "PRODUCT");
			m.addAttribute("totalPage", productList.getTotalPages());

//		trang bắt đầu là 0
			m.addAttribute("currentPage", productList.getNumber() + 1);
			m.addAttribute("categoryList", categoryService.findAllByActivated());

			m.addAttribute("searchProduct", "search Product");
			m.addAttribute("success", "Activated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("failed", "Activated Failed");
		}
		return "products";
	}

	@GetMapping("/products/search/deleteById")
	public String deleteSearchProduct(Long id, Model m, String keyword, @RequestParam(name = "pageNo") int pageNo, Authentication authentication) {
		try {
			Admin admin = adminService.findByUsername(authentication.getName());
			m.addAttribute("admin", admin);
			this.productService.deleteById(id);
			Page<Product> productList = this.productService.searchProducts(keyword, pageNo);

			m.addAttribute("productList", productList);
			m.addAttribute("keyword", keyword);

			m.addAttribute("title", "PRODUCT");
			m.addAttribute("totalPage", productList.getTotalPages());

//		trang bắt đầu là 0
			if(productList.isEmpty() == false ) {
				m.addAttribute("currentPage", productList.getNumber() + 1);
				m.addAttribute("categoryList", categoryService.findAllByActivated());

				m.addAttribute("searchProduct", "search Product");
				m.addAttribute("success", "Delete Successfully");
				String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
				String encodedURL = "redirect:/products/search?keyword=" + encodedKeyword + "&pageNo=" + pageNo;
				return encodedURL;
			}else {
				m.addAttribute("currentPage", productList.getNumber());
				m.addAttribute("categoryList", categoryService.findAllByActivated());

				m.addAttribute("searchProduct", "search Product");
				m.addAttribute("success", "Delete Successfully");
				
				if(pageNo -1 >= 1) {
					pageNo = pageNo -1;
				}
				
				String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
				String encodedURL = "redirect:/products/search?keyword=" + encodedKeyword + "&pageNo=" + pageNo;
				return encodedURL;
			}


		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("failed", "Delete Failed");
		}
		return "products";
	}

	@PostMapping(value = "/products/search/update")
	public String updateSearchProduct(ProductDto productDto, @RequestParam("imageProduct") MultipartFile imageProduct,
			Model m, String keyword, @RequestParam(name = "pageNo") int pageNo, Authentication authentication) {

		Product product = this.productService.update(productDto, imageProduct);
		Page<Product> productList = this.productService.searchProducts(keyword, pageNo);
		
		Admin admin = adminService.findByUsername(authentication.getName());
		m.addAttribute("admin", admin);

		try {
			if (product != null) {
				m.addAttribute("productList", productList);
				m.addAttribute("keyword", keyword);

				m.addAttribute("title", "PRODUCT");
				m.addAttribute("totalPage", productList.getTotalPages());

//			trang bắt đầu là 0
				m.addAttribute("currentPage", productList.getNumber() + 1);
				m.addAttribute("categoryList", categoryService.findAllByActivated());

				m.addAttribute("searchProduct", "search Product");

				m.addAttribute("success", "Update Successfully");
			} else {
				m.addAttribute("failed", "Update Failed because Duplicate ");
			}
		} catch (Exception e) {
			m.addAttribute("failed", "Update Failed");
		}
		return "products";
	}

}
