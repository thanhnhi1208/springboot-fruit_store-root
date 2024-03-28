package com.nhi.customer.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhi.libary.dto.CustomerDto;
import com.nhi.libary.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	private CustomerService customerService;

	@GetMapping("/login")
	public String login(Model m, Authentication authentication, HttpSession session) {
		m.addAttribute("title", "LOGIN");
		
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model m) {
		m.addAttribute("title", "REGISTER");
		
		CustomerDto customerDto=(CustomerDto) m.asMap().get("customerDto");	
		if(customerDto != null) {
			m.addAttribute("customerDto", customerDto);
		}else {
			m.addAttribute("customerDto", new CustomerDto());
		}
		return "register";
	}
	
	@PostMapping("/register-new")
	public String registerNew(@Valid CustomerDto customerDto,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		try {
			if(bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerDto", bindingResult);
				redirectAttributes.addFlashAttribute("customerDto", customerDto);
				return "redirect:/register";
			}
			
			if(this.customerService.findByUsername(customerDto.getUsername()) != null) {
				redirectAttributes.addFlashAttribute("customerDto", customerDto);
				redirectAttributes.addFlashAttribute("failed", "Username already exists!");
				return "redirect:/register";
			}
			
			if(customerDto.getPassword().equals(customerDto.getRepeatPassword()) == false) {
				redirectAttributes.addFlashAttribute("customerDto", customerDto);
				redirectAttributes.addFlashAttribute("failed", "Check password and repeat password again!");
			}else {
				redirectAttributes.addFlashAttribute("success", "Sign up Successfully");
				this.customerService.save(customerDto);
			}
			
			return "redirect:/register";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("customerDto", customerDto);
			redirectAttributes.addFlashAttribute("failed", "Failed Server!");
			return "redirect:/register";
		}
	}
}
