package com.nhi.libary.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Order;
import com.nhi.libary.model.Product;
import com.nhi.libary.model.ShoppingCart;

public interface OrderService {
	
//	customer

	void saveOrder(ShoppingCart shoppingCart, String paymentMethod);
	
	void cancelOrder(Long id) ;
	
	List<Order>  findAll();
	
	List<Order> findByCustomer(Customer customer);
		
//admin
	
	
	Page<Order> orderPage(int pageNo);

	Page<Order> searchOrders(String keyword, int pageNo);

	void acceptOrder(Long id);

	void refuseById(Long id);
}
