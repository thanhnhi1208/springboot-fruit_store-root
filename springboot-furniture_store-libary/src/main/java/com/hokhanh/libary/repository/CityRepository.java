package com.hokhanh.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hokhanh.libary.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

	@Query(value = "SELECT * FROM cities WHERE country_id = :id", nativeQuery = true)
	List<City> findByIdOfCountry(@Param("id") Long id);
}
