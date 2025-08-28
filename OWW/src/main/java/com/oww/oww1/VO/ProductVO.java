// src/main/java/com/oww/oww1/vo/ProductVO.java
package com.oww.oww1.VO;

import lombok.Data;

//ProductVO
@Data
public class ProductVO {
	private int product_no;
	private String product_name; // <- getProduct_name() 제공
	private int category; // 0 hall, 1 studio, 2 dress, 3 makeup
	private int cost;
	private String address;
	private String description;
	private String img;

	public int getProductNo() {
		return product_no;
	}

	public String getProductName() {
		return product_name;
	}

	public int getCategory() {
		return category;
	}

	public int getCost() {
		return cost;
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public String getImg() {
		return img;
	}
}
