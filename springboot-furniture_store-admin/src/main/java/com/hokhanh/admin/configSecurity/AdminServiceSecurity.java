package com.hokhanh.admin.configSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hokhanh.libary.model.Admin;
import com.hokhanh.libary.repository.AdminRepository;

@Service
public class AdminServiceSecurity implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = this.adminRepository.findByUsername(username);
		
		if(admin == null) {
			throw new UsernameNotFoundException("Could not find "+username);
		}
		
		return new AdminDetails(admin);
	}

}
