package com.hokhanh.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages =  {"com.hokhanh.libary", "com.hokhanh.customer"})
@EnableJpaRepositories(value = "com.hokhanh.libary.repository") 
@EntityScan(value =   "com.hokhanh.libary.model")
public class SpringbootFurnitureStoreCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFurnitureStoreCustomerApplication.class, args);
	}

}
