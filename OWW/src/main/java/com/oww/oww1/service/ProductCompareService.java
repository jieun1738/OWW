package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.ProductVO;

import jakarta.servlet.http.HttpSession;

public interface ProductCompareService {
	void add(HttpSession session, int productNo);

	void remove(HttpSession session, int productNo);

	void reset(HttpSession session);

	List<ProductVO> list(HttpSession session);

	int size(HttpSession session);
}
