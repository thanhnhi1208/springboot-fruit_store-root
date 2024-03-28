package com.nhi.libary.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUpload {

	private final String UPLOAD_FOLDER_PRODUCT = "D:\\SpringToolSuite\\SpringBoot\\springboot-furniture_store-root\\springboot-furniture_store-admin\\src\\main\\resources\\static\\img\\img-product";

	private final String UPLOAD_FOLDER_ADMIN = "D:\\SpringToolSuite\\SpringBoot\\springboot-furniture_store-root\\springboot-furniture_store-admin\\src\\main\\resources\\static\\img\\img-admin";
	
	private final String UPLOAD_FOLDER_CUSTOMER = "D:\\SpringToolSuite\\SpringBoot\\springboot-furniture_store-root\\springboot-furniture_store-customer\\src\\main\\resources\\static\\images\\img-customer";

	
	public void uploadImage(MultipartFile image) {
		try {
			Files.copy(image.getInputStream(), Paths.get(UPLOAD_FOLDER_PRODUCT + File.separator, image.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void uploadImageAdmin(MultipartFile image) {
		try {
			Files.copy(image.getInputStream(), Paths.get(UPLOAD_FOLDER_ADMIN + File.separator, image.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void uploadImageCustomer(MultipartFile image) {
		try {
			Files.copy(image.getInputStream(), Paths.get(UPLOAD_FOLDER_CUSTOMER + File.separator, image.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
