package com.oww.oww1.service;

import com.oww.oww1.mapper.PlanMapper;
import com.oww.oww1.VO.PlanVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanCompareServiceImpl implements PlanCompareService {

    private static final String ATTR = "planner.compare.plans";
    private static final int MAX = 2;

    private final PlanMapper planMapper;

    @Override
    public void toggle(HttpSession session, long planNo) {
        @SuppressWarnings("unchecked")
        LinkedHashSet<Long> box = (LinkedHashSet<Long>) session.getAttribute(ATTR);
        if (box == null) box = new LinkedHashSet<>();
        if (box.contains(planNo)) box.remove(planNo);
        else {
            box.add(planNo);
            while (box.size() > MAX) {
                Iterator<Long> it = box.iterator();
                if (it.hasNext()) { it.next(); it.remove(); }
            }
        }
        session.setAttribute(ATTR, box);
    }

    @Override
    public void reset(HttpSession session) {
        session.removeAttribute(ATTR);
    }

    @Override
    public List<PlanVO> list(HttpSession session) {
        @SuppressWarnings("unchecked")
        LinkedHashSet<Long> box = (LinkedHashSet<Long>) session.getAttribute(ATTR);
        if (box == null || box.isEmpty()) return List.of();
        return box.stream()
                .map(planMapper::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public int size(HttpSession session) {
        @SuppressWarnings("unchecked")
        LinkedHashSet<Long> box = (LinkedHashSet<Long>) session.getAttribute(ATTR);
        return box == null ? 0 : box.size();
    }
}
