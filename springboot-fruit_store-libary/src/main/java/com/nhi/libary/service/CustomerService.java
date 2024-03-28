package com.nhi.libary.service;

import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.dto.CustomerDto;
import com.nhi.libary.model.Customer;

public interface CustomerService {

	Customer findByUsername(String username);
	
	Customer save(CustomerDto customerDto);
	
	Customer update(Customer customer, MultipartFile customerImage);
	
}
