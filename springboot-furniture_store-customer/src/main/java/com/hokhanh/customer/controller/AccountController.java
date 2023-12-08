package com.hokhanh.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hokhanh.libary.model.City;
import com.hokhanh.libary.model.Country;
import com.hokhanh.libary.model.Customer;
import com.hokhanh.libary.model.Product;
import com.hokhanh.libary.model.ShoppingCart;
import com.hokhanh.libary.service.CityService;
import com.hokhanh.libary.service.CountryService;
import com.hokhanh.libary.service.CustomerService;
import com.hokhanh.libary.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CountryService countryService;
	
	public void checkTotalItemOfCustomer(Authentication authentication, Model model) {
		if (authentication != null) {
			Customer customer = this.customerService.findByUsername(authentication.getName());
			ShoppingCart shoppingCart = customer.getShoppingCarts();
			if (shoppingCart != null) {
				model.addAttribute("totalItems", shoppingCart.getTotalItems());
			}
		}
	}
	
	@GetMapping("/account")
	public String accountPage(Model m, Authentication authentication, HttpSession session) {
		m.addAttribute("title", "Profile");
		Customer customer = this.customerService.findByUsername(authentication.getName());
		
		Customer temp =  (Customer) m.asMap().get("customer");
		
		
		if(temp == null) {
			m.addAttribute("customer", customer);	
		}else {
			m.addAttribute("customer", temp);
		}
							
		List<Country> countryList = this.countryService.findAll();
		m.addAttribute("countryList", countryList);
		
		List<City> cityList = this.cityService.findAll();
		m.addAttribute("cityList", cityList);
		
		checkTotalItemOfCustomer(authentication, m);
		
		session.setAttribute("customer", customer);
		
		return "customer-signup";
	}
	
	@GetMapping("/account/getCityById")
	@ResponseBody
	public List<City> getCityById(Long id) {
		List<City> cityList = this.cityService.findByIdOfCountry(id);
		
		return cityList;
	}
	
	@PostMapping("/account/updateCustomer")
	public String updateCustomer(Customer customer ,RedirectAttributes redirectAttributes, MultipartFile customerImage) {
		Customer temp = this.customerService.update(customer, customerImage);
		
		if(temp != null && temp.getPhoneNumber() == null) {
			redirectAttributes.addFlashAttribute("phoneNumberError", "Số điện thoại của bạn đã tồn tại");
			redirectAttributes.addFlashAttribute("customer", customer);
		}else if(temp != null && temp.getAccountNumber() == null) {
			redirectAttributes.addFlashAttribute("accountNumberError", "Số tài khoản của bạn đã tồn tại");
			redirectAttributes.addFlashAttribute("customer", customer);
		}else {
			redirectAttributes.addFlashAttribute("success", "Bạn đã chỉnh sửa thành công");
			redirectAttributes.addFlashAttribute("customer", customer);
		}				
		return "redirect:/account";
	}
}
