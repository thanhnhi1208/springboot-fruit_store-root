package com.hokhanh.libary.service;

import org.springframework.web.multipart.MultipartFile;

import com.hokhanh.libary.dto.AdminDto;
import com.hokhanh.libary.model.Admin;

public interface AdminService {

	Admin findByUsername(String username);
	
	Admin save(AdminDto adminDto);
	
	Admin update(Admin admin, MultipartFile adminImage);
}
