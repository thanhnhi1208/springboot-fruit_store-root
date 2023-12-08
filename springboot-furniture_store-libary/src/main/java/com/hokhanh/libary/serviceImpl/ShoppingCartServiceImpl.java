package com.hokhanh.libary.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hokhanh.libary.model.CartItem;
import com.hokhanh.libary.model.Customer;
import com.hokhanh.libary.model.Product;
import com.hokhanh.libary.model.ShoppingCart;
import com.hokhanh.libary.repository.CartItemRepository;
import com.hokhanh.libary.repository.CustomerRepository;
import com.hokhanh.libary.repository.ShoppingCartRepository;
import com.hokhanh.libary.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Override
	public ShoppingCart addItemToCart(Product product, int quantity, String username) {
		Customer customer = this.customerRepository.findByUsername(username);
		
		ShoppingCart shoppingCart = customer.getShoppingCarts();
		
		if(shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			
			CartItem cartItem = new CartItem();
			List<ShoppingCart>shoppingCarts = new ArrayList<>();
			cartItem.setCart(shoppingCarts);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			
			if(product.getSalePrice() >0) {
				cartItem.setTotalPrice(product.getSalePrice() * quantity);
			}else {
				cartItem.setTotalPrice(product.getCostPrice() * quantity);
			}
			
			double newTotalPrice = Math.round(cartItem.getTotalPrice()*100.0)/100.0;
			cartItem.setTotalPrice(newTotalPrice);
			
			List<CartItem> cartItems = new ArrayList<>();
			cartItems.add(cartItem);
			shoppingCart.setCartItems(cartItems);
			
			this.cartItemRepository.save(cartItem);
			
		}else {
			CartItem cartItem = this.find(shoppingCart.getCartItems(), product.getId());
			if(cartItem == null) {
				cartItem = new CartItem();
				List<ShoppingCart>shoppingCarts = new ArrayList<>();
				cartItem.setCart(shoppingCarts);
				cartItem.setProduct(product);
				cartItem.setQuantity(quantity);
				
				if(product.getSalePrice() >0) {
					cartItem.setTotalPrice(product.getSalePrice() * quantity);
				}else {
					cartItem.setTotalPrice(product.getCostPrice() * quantity);
				}
				
				double newTotalPrice = Math.round(cartItem.getTotalPrice()*100.0)/100.0;
				cartItem.setTotalPrice(newTotalPrice);
				
				shoppingCart.getCartItems().add(cartItem);
				
				this.cartItemRepository.save(cartItem);
			}else {
				for (CartItem c : shoppingCart.getCartItems()) {
					if(c.getProduct().getId() == product.getId()) {
						c.setQuantity(cartItem.getQuantity() + quantity);
						
						if(product.getSalePrice() >0) {
							c.setTotalPrice(c.getProduct().getSalePrice() * c.getQuantity());
						}else {
							c.setTotalPrice(c.getProduct().getCostPrice() * c.getQuantity());
						}
						
						double newTotalPrice = Math.round(c.getTotalPrice()*100.0)/100.0;
						c.setTotalPrice(newTotalPrice);
					}
				}
				
				this.cartItemRepository.save(cartItem);
			}
		}
		
		shoppingCart.setCustomer(customer);
		shoppingCart.setTotalItems(this.totalItem(shoppingCart.getCartItems()));
		shoppingCart.setTotalPrice(this.totalPrice(shoppingCart.getCartItems()));
		return this.shoppingCartRepository.save(shoppingCart);	
	}
	
	private CartItem find(List<CartItem> cartItems, Long productId) {
		if(cartItems == null) {
			return null;
		}
		
		CartItem cartItem = null;
		for (CartItem cart : cartItems) {
			if(cart.getProduct().getId() == productId) {
				cartItem = cart;
			}
		}
		
		return cartItem;
	}
	
	private int totalItem(List<CartItem> cartItems) {
		int sum = 0;
		if(cartItems == null) {
			return sum;
		}
		
		for (CartItem cartItem : cartItems) {
			if(cartItem != null) {
				sum +=1;
			}
		}
		
		return sum;
	}
	
	private double totalPrice(List<CartItem> cartItems) {
		double totalPrice =0;
		
		if(cartItems == null) {
			return totalPrice;
		}
		
		for (CartItem cartItem : cartItems) {
			if(cartItem !=null) {
				if(cartItem.getProduct().getSalePrice() >0) {
					totalPrice += cartItem.getProduct().getSalePrice() * cartItem.getQuantity();
				}else {
					totalPrice += cartItem.getProduct().getCostPrice() * cartItem.getQuantity();
				}				
			}
		}
		
		double newTotalPrice = Math.round(totalPrice*100.0)/100.0;
		
		return newTotalPrice;
	}	

	@Override
	public ShoppingCart updateItemInCart(Product product, int quantity, String username) {
		Customer customer = this.customerRepository.findByUsername(username);
		
		ShoppingCart shoppingCart = customer.getShoppingCarts();
		
		List<CartItem> cartItems = shoppingCart.getCartItems();
		
		CartItem cartItem = this.find(cartItems, product.getId());
		if(cartItem != null) {
			cartItem.setQuantity(quantity);
			
			if(cartItem.getProduct().getSalePrice() >0) {
				cartItem.setTotalPrice(quantity*product.getSalePrice());	
			}else {
				cartItem.setTotalPrice(quantity*product.getCostPrice());	
			}
			
			double newTotalPrice = Math.round(cartItem.getTotalPrice()*100.0)/100.0;
			cartItem.setTotalPrice(newTotalPrice);
			
			this.cartItemRepository.save(cartItem);
			
			shoppingCart.setTotalItems(this.totalItem(cartItems));
			shoppingCart.setTotalPrice(this.totalPrice(cartItems));
			return this.shoppingCartRepository.save(shoppingCart);
		}else {
			return null;
		}
	}

	@Override
	public ShoppingCart deleteItemInCart(Product product, String username) {
		Customer customer = this.customerRepository.findByUsername(username);
		
		ShoppingCart shoppingCart = customer.getShoppingCarts();
		List<CartItem>cartItems = shoppingCart.getCartItems();
		
		CartItem cartItem =this.find(cartItems, product.getId());	
		if(cartItem == null) {
			return null;
		}
		
		cartItems.remove(cartItem);
		shoppingCart.setCartItems(cartItems);
		
		this.cartItemRepository.deleteById(cartItem.getId());
		
		shoppingCart.setTotalItems(this.totalItem(cartItems));
		shoppingCart.setTotalPrice(this.totalPrice(cartItems));
		return this.shoppingCartRepository.save(shoppingCart);
	}

}
