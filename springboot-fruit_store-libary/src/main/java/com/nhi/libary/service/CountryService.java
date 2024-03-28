package com.nhi.libary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhi.libary.model.Country;


public interface CountryService {

	List<Country> findAll();
}
