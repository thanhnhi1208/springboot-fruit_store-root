package com.hokhanh.libary.model;

import java.time.LocalDate;
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
import jakarta.persistence.ManyToOne;
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
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	private LocalDate orderDate;
	
	private LocalDate deliveryDate;
	
	private String orderStatus;
	
	private double totalPrice;
	
	
	private int quantity;
	
	private String paymentMethod;
	
	private boolean isAccept;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private Customer customer;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private List<CartItem> cartItem;
}
