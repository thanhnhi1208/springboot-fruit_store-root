package com.hokhanh.libary.service;

import java.util.List;

import com.hokhanh.libary.model.City;

public interface CityService {

	List<City> findAll();
	
	List<City> findByIdOfCountry(Long id);
}
