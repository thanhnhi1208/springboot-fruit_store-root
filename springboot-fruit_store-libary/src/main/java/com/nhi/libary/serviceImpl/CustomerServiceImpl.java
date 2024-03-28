package com.nhi.libary.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.dto.CustomerDto;
import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Role;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.repository.RoleRepository;
import com.nhi.libary.service.CustomerService;
import com.nhi.libary.utils.ImageUpload;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ImageUpload imageUpload;

	@Override
	public Customer findByUsername(String username) {
		return customerRepository.findByUsername(username);
	}

	@Override
	public Customer save(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setFirstName(customerDto.getFirstName());
		customer.setLastName(customerDto.getLastName());
		customer.setUsername(customerDto.getUsername());
		customer.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
		
		List<Role> roles = new ArrayList<>();
		roles.add(this.roleRepository.findByName("CUSTOMER"));
		customer.setRoleList(roles);
		
		return this.customerRepository.save(customer);
	}

	@Override
	public Customer update(Customer customer, MultipartFile customerImage) {
		
		try {
			Customer temp_phoneNumber = this.customerRepository.findByPhoneNumber(customer.getPhoneNumber());
			
			Customer temp_accountNumber = this.customerRepository.findByAccountNumber(customer.getAccountNumber());
			if(temp_phoneNumber != null && temp_phoneNumber.getId() != customer.getId()) {
				temp_phoneNumber.setPhoneNumber(null);
				return temp_phoneNumber;
			}else if(temp_accountNumber != null && temp_accountNumber.getId() != customer.getId()) {
				temp_accountNumber.setAccountNumber(null);
				return temp_accountNumber;
			}else {
				if(customerImage.isEmpty() == false) {
					imageUpload.uploadImageCustomer(customerImage);
					customer.setImage(Base64.getEncoder().encodeToString(customerImage.getBytes()));
				}else {
					Customer temp = this.customerRepository.findByUsername(customer.getUsername());
					customer.setImage(temp.getImage());
				}			
				
				return this.customerRepository.save(customer);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	

}
