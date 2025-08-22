package com.oww.oww1.service;

import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.ProductMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCompareServiceImpl implements ProductCompareService {

    private static final String ATTR = "planner.compare.products";
    private static final int MAX = 4;

    private final ProductMapper productMapper;

    @Override
    public void add(HttpSession session, long productNo) {
        @SuppressWarnings("unchecked")
        LinkedHashSet<Long> box = (LinkedHashSet<Long>) session.getAttribute(ATTR);
        if (box == null) box = new LinkedHashSet<>();
        box.remove(productNo);
        box.add(productNo);
        while (box.size() > MAX) {
            Iterator<Long> it = box.iterator();
            if (it.hasNext()) { it.next(); it.remove(); }
        }
        session.setAttribute(ATTR, box);
    }

    @Override
    public void remove(HttpSession session, long productNo) {
        @SuppressWarnings("unchecked")
        LinkedHashSet<Long> box = (LinkedHashSet<Long>) session.getAttribute(ATTR);
        if (box == null) return;
        box.remove(productNo);
        session.setAttribute(ATTR, box);
    }

    @Override
    public void reset(HttpSession session) {
        session.removeAttribute(ATTR);
    }

    @Override
    public List<ProductVO> list(HttpSession session) {
        @SuppressWarnings("unchecked")
        LinkedHashSet<Long> box = (LinkedHashSet<Long>) session.getAttribute(ATTR);
        if (box == null || box.isEmpty()) return List.of();
        return box.stream()
                .map(productMapper::findById)
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
