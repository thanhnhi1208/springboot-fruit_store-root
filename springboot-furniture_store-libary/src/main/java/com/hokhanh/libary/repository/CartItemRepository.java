package com.hokhanh.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.libary.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
