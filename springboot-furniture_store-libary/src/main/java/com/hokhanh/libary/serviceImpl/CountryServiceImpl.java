package com.hokhanh.libary.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hokhanh.libary.model.Country;
import com.hokhanh.libary.repository.CountryRepository;
import com.hokhanh.libary.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	public List<Country> findAll(){
		return this.countryRepository.findAll();
	}
}
