package com.oww.oww1.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.mapper.PlanMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanCompareServiceImpl implements PlanCompareService {

	private final PlanMapper planMapper;
	private static final String ATTR = "planner.compare.plan";

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
	public void toggle(HttpSession session, int planNo) {
		LinkedHashSet<Integer> box = getBox(session);
		if (box.contains(planNo))
			box.remove(planNo);
		else
			box.add(planNo);
	}

	@Override
	public void reset(HttpSession session) {
		session.removeAttribute(ATTR);
	}

	@Override
	public List<PlanVO> list(HttpSession session) {
		@SuppressWarnings("unchecked")
		LinkedHashSet<Integer> box = (LinkedHashSet<Integer>) session.getAttribute(ATTR);
		if (box == null || box.isEmpty())
			return List.of();
		return box.stream().map(planMapper::findById).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public int size(HttpSession session) {
		@SuppressWarnings("unchecked")
		LinkedHashSet<Integer> box = (LinkedHashSet<Integer>) session.getAttribute(ATTR);
		return box == null ? 0 : box.size();
	}
}
