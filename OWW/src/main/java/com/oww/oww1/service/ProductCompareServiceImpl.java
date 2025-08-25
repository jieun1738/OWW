package com.oww.oww1.service;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.ProductMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCompareServiceImpl implements ProductCompareService {

	private final ProductMapper productMapper;
	private static final String ATTR = "planner.compare.product";

	@SuppressWarnings("unchecked")
	private LinkedHashSet<Integer> getBox(HttpSession session) {
		LinkedHashSet<Integer> box = (LinkedHashSet<Integer>) session.getAttribute(ATTR);
		if (box == null) {
			box = new LinkedHashSet<>();
			session.setAttribute(ATTR, box);
		}
		return box;
	}

	@Override
	public void add(HttpSession session, int productNo) {
		LinkedHashSet<Integer> box = getBox(session);
		if (box.size() >= 4) { // 최대 4개
			// 가장 오래된 것 제거
			Iterator<Integer> it = box.iterator();
			if (it.hasNext()) {
				it.next();
				it.remove();
			}
		}
		box.add(productNo);
	}

	@Override
	public void remove(HttpSession session, int productNo) {
		LinkedHashSet<Integer> box = getBox(session);
		box.remove(productNo);
	}

	@Override
	public void reset(HttpSession session) {
		session.removeAttribute(ATTR);
	}

	@Override
	public List<ProductVO> list(HttpSession session) {
		@SuppressWarnings("unchecked")
		LinkedHashSet<Integer> box = (LinkedHashSet<Integer>) session.getAttribute(ATTR);
		if (box == null || box.isEmpty())
			return List.of();
		return box.stream().map(productMapper::findById).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public int size(HttpSession session) {
		@SuppressWarnings("unchecked")
		LinkedHashSet<Integer> box = (LinkedHashSet<Integer>) session.getAttribute(ATTR);
		return box == null ? 0 : box.size();
	}
}
