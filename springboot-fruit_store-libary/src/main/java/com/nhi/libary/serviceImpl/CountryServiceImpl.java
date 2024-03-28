package com.nhi.libary.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.Country;
import com.nhi.libary.repository.CountryRepository;
import com.nhi.libary.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	public List<Country> findAll(){
		return this.countryRepository.findAll();
	}
}
