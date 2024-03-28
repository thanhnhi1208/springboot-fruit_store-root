package com.nhi.libary.service;

import java.util.List;

import com.nhi.libary.model.City;

public interface CityService {

	List<City> findAll();
	
	List<City> findByIdOfCountry(Long id);
}
