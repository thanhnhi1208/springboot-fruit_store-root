package com.nhi.libary.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.dto.AdminDto;
import com.nhi.libary.model.Admin;
import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Role;
import com.nhi.libary.repository.AdminRepository;
import com.nhi.libary.repository.RoleRepository;
import com.nhi.libary.service.AdminService;
import com.nhi.libary.utils.ImageUpload;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ImageUpload imageUpload;
	
	@Override
	public Admin findByUsername(String username) {
		return this.adminRepository.findByUsername(username);
	}

	@Override
	public Admin save(AdminDto adminDto) {
		Admin admin = new Admin();
		admin.setFirstName(adminDto.getFirstName());
		admin.setLastName(adminDto.getLastName());
		admin.setUsername(adminDto.getUsername());
		admin.setPassword(adminDto.getPassword());
		
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(this.roleRepository.findByName("ADMIN"));	
		admin.setRoleList(roleList);
		return this.adminRepository.save(admin);
	}

	@Override
	public Admin update(Admin admin, MultipartFile adminImage) {
		try {
			Admin temp_phoneNumber = this.adminRepository.findByPhoneNumber(admin.getPhoneNumber());
			
			if(temp_phoneNumber != null && temp_phoneNumber.getId() != admin.getId()) {
				temp_phoneNumber.setPhoneNumber(null);
				return temp_phoneNumber;
			}else {
				if(adminImage.isEmpty() == false) {
					imageUpload.uploadImageCustomer(adminImage);
					admin.setImage(Base64.getEncoder().encodeToString(adminImage.getBytes()));
				}else {
					Admin temp = this.adminRepository.findByUsername(admin.getUsername());
					admin.setImage(temp.getImage());
				}			
				
				return this.adminRepository.save(admin);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
