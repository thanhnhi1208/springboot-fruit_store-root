package com.hokhanh.libary.service;

import com.hokhanh.libary.model.Customer;
import com.hokhanh.libary.model.Product;
import com.hokhanh.libary.model.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart addItemToCart(Product product, int quantity, String username);
	
	ShoppingCart updateItemInCart(Product product, int quantity, String username);
	
	ShoppingCart deleteItemInCart(Product product, String username);
}
