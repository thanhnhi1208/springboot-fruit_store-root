package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByUsername(String username);

	Admin findByPhoneNumber(String phoneNumber);
}
