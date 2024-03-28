package com.nhi.admin.controller;

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

import com.nhi.libary.model.Admin;
import com.nhi.libary.model.City;
import com.nhi.libary.model.Country;
import com.nhi.libary.model.Customer;
import com.nhi.libary.service.AdminService;
import com.nhi.libary.service.CityService;
import com.nhi.libary.service.CountryService;

@Controller
public class ProfileAdmin {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private CityService cityService;


	@GetMapping("profile")
	public String profile(Authentication authentication, Model m) {
		m.addAttribute("title", "Profile");
		
		Admin admin = this.adminService.findByUsername(authentication.getName());
		List<Country > countryList = this.countryService.findAll();
		List<City> cityList = this.cityService.findAll();
		
		Admin temp =  (Admin) m.asMap().get("admin");
		if(temp == null) {
			m.addAttribute("admin", admin);	
		}else {
			m.addAttribute("admin", temp);
		}
		
		m.addAttribute("admin", admin);
		m.addAttribute("countryList", countryList);
		m.addAttribute("cityList", cityList);
		return "profile";
	}
	
	@GetMapping("/account/getCityById")
	@ResponseBody
	public List<City> getCityById(Long id) {
		List<City> cityList = this.cityService.findByIdOfCountry(id);
		
		return cityList;
	}
	
	@PostMapping("/updateAdmin")
	public String updateAdmin(Admin admin ,RedirectAttributes redirectAttributes, MultipartFile adminImage) {
		Admin temp = this.adminService.update(admin, adminImage);
		
		if(temp != null && temp.getPhoneNumber() == null) {
			redirectAttributes.addFlashAttribute("phoneNumberError", "Số điện thoại của bạn đã tồn tại");
			redirectAttributes.addFlashAttribute("admin", admin);
		}else {
			redirectAttributes.addFlashAttribute("success", "Bạn đã chỉnh sửa thành công");
			redirectAttributes.addFlashAttribute("admin", admin);
		}				
		return "redirect:/profile";
	}
}
