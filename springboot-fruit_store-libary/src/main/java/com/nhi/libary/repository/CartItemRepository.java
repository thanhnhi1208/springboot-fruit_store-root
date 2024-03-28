package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
