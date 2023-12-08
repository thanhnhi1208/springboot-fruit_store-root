package com.hokhanh.libary.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopping_cart_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private Customer customer;
	
	private double totalPrice;
	
	private int totalItems;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "shopping_carts_cart_items", 
		joinColumns = @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
		,inverseJoinColumns = @JoinColumn(name = "cart_items_id", referencedColumnName = "cart_items_id"))	
	private List<CartItem> cartItems;
}
