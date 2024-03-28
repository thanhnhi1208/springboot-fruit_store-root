package com.nhi.customer.configSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.Customer;
import com.nhi.libary.repository.CustomerRepository;

@Service
public class CustomerServiceConfig implements UserDetailsService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer =this.customerRepository.findByUsername(username);
		if(customer == null) {
			throw new UsernameNotFoundException("Could not find username");
		}
		
		return new CustomerDetails(customer);
	}

}
