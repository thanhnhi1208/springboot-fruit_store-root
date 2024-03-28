package com.nhi.libary.service;

import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.dto.AdminDto;
import com.nhi.libary.model.Admin;

public interface AdminService {

	Admin findByUsername(String username);
	
	Admin save(AdminDto adminDto);
	
	Admin update(Admin admin, MultipartFile adminImage);
}
