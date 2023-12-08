package com.hokhanh.libary.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products", uniqueConstraints =  @UniqueConstraint(columnNames = {"name", "image"} ))
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	private String description;
	private int currentQuantity;
	private double costPrice;
	private double salePrice;
	
	private int sold;
	
	@Lob
	@Column(name ="image",columnDefinition = "MEDIUMBLOB", length = 100)
	private String image;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;
	
	private boolean activated;
	private boolean hidden;
}
