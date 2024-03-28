package com.nhi.libary.dto;

import com.nhi.libary.model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Long id;

	private String name;
	private String description;
	private int currentQuantity;
	private double costPrice;
	private double salePrice;

	private String image;

	private Category category;

	private boolean activated;
	private boolean hidden;
}
