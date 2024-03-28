package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.dto.CustomerDto;
import com.nhi.libary.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByUsername(String username);
	
	Customer findByPhoneNumber(String phongNumber);
	
	Customer findByAccountNumber(String accountNumber);
	
}
