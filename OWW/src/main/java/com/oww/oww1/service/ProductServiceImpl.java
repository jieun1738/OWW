// ProductServiceImpl.java
package com.oww.oww1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductMapper mapper;

	@Override
	public List<ProductVO> listAll(String q, String exPattern, String sort) {
		return mapper.findAll(q, exPattern, sort);
	}

	@Override
	public List<ProductVO> listByCategory(int cat, String q, String exPattern, String sort) {
		return mapper.findByCategory(cat, q, exPattern, sort);
	}

	@Override
	public ProductVO getOne(int productNo) {
		return mapper.findByNo(productNo);
	}
}
