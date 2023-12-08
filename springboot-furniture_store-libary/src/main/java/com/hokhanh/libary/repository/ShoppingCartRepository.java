package com.hokhanh.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.libary.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
