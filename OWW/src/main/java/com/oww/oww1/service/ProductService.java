// ProductService.java
package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.ProductVO;

public interface ProductService {
	List<ProductVO> listAll(String q, String exPattern, String sort);

	List<ProductVO> listByCategory(int cat, String q, String exPattern, String sort);

	ProductVO getOne(int productNo);
}
