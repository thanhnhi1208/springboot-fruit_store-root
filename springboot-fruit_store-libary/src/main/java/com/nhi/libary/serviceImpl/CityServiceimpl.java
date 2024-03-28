package com.nhi.libary.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.City;
import com.nhi.libary.repository.CityRepository;
import com.nhi.libary.service.CityService;

@Service
public class CityServiceimpl implements CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	
	@Override
	public List<City> findAll() {
		return this.cityRepository.findAll();
	}

	@Override
	public List<City> findByIdOfCountry(Long id) {
		return this.cityRepository.findByIdOfCountry(id);
	}
	
	

}
