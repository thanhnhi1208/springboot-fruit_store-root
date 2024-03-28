package com.nhi.libary.service;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Product;
import com.nhi.libary.model.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart addItemToCart(Product product, int quantity, String username);
	
	ShoppingCart updateItemInCart(Product product, int quantity, String username);
	
	ShoppingCart deleteItemInCart(Product product, String username);
}
