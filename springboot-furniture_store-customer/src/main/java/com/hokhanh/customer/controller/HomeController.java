package com.hokhanh.customer.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hokhanh.libary.model.Customer;
import com.hokhanh.libary.model.Product;
import com.hokhanh.libary.model.ShoppingCart;
import com.hokhanh.libary.repository.CustomerRepository;
import com.hokhanh.libary.service.CategoryService;
import com.hokhanh.libary.service.CustomerService;
import com.hokhanh.libary.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/home")
	public String homePage(Model m, Authentication authentication, HttpSession session) {
		m.addAttribute("title", "HOME PAGE");
		
		if(authentication !=null ) {
			String username = authentication.getName();
			session.setAttribute("username", username);
			
			Customer customer = this.customerService.findByUsername(username);
			System.out.println(customer.getAccountNumber());
			session.setAttribute("customer", customer);
			ShoppingCart shoppingCart =customer.getShoppingCarts();
			if(shoppingCart != null) {
				m.addAttribute("totalItems", shoppingCart.getTotalItems());
			}
		}
		
		Customer customer = this.customerService.findByUsername(authentication.getName());
		ShoppingCart shoppingCart = customer.getShoppingCarts();
		m.addAttribute("shoppingCart", shoppingCart);
		
		List<Product> products = this.productService.getAllProduct();
		m.addAttribute("productFeed", products);
		return "home";
	}
	
	@GetMapping("/getTotalItems")
	@ResponseBody
	public String getTotalItems(Authentication authentication) {
		if(authentication != null) {
			Customer customer = this.customerService.findByUsername(authentication.getName());
			ShoppingCart shoppingCart =customer.getShoppingCarts();
			if(shoppingCart != null) {
				return shoppingCart.getTotalItems()+"";
			}
		}

		return null;
	}
	
	@GetMapping("/checkAuthentication")
	@ResponseBody
	public String checkAuthentication(Authentication authentication) {
		System.out.println("asda");
		if(authentication != null) {
			return "yes";
		}
		
		return "no";
	}
	
	
	

}
