package com.hokhanh.libary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hokhanh.libary.model.Country;


public interface CountryService {

	List<Country> findAll();
}
