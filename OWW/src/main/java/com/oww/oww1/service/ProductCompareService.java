package com.oww.oww1.service;

import com.oww.oww1.VO.ProductVO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ProductCompareService {
    void add(HttpSession session, long productNo);
    void remove(HttpSession session, long productNo);
    void reset(HttpSession session);
    List<ProductVO> list(HttpSession session);
    int size(HttpSession session);
}
