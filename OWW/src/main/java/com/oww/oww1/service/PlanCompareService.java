package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PlanVO;

import jakarta.servlet.http.HttpSession;

public interface PlanCompareService {
	void toggle(HttpSession session, int planNo);

	void reset(HttpSession session);

	List<PlanVO> list(HttpSession session);

	int size(HttpSession session);
}
