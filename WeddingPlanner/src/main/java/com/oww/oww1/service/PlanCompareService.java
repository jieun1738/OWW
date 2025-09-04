package com.wedding.oww.service;

import com.wedding.oww.vo.PlanVO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface PlanCompareService {
    void toggle(HttpSession session, long planNo);
    void reset(HttpSession session);
    List<PlanVO> list(HttpSession session);
    int size(HttpSession session);
}
