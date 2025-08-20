package com.wedding.oww.service;

import com.wedding.oww.vo.ProductVO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ProductCompareService {
    void add(HttpSession session, long productNo);
    void remove(HttpSession session, long productNo);
    void reset(HttpSession session);
    List<ProductVO> list(HttpSession session);
    int size(HttpSession session);
}
