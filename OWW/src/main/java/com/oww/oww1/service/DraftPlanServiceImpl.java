package com.oww.oww1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.DraftPlanVO;

import jakarta.servlet.http.HttpSession;

/**
 * 임시 플랜 세션 보관 (간단 자료형만 사용)
 * category: 0=hall,1=studio,2=dress,3=makeup
 */
@Service
public class DraftPlanServiceImpl implements DraftPlanService {

    private static final String KEY_CURRENT = "draft_current";
    private static final String KEY_LIST = "draft_list";

    @SuppressWarnings("unchecked")
    private List<DraftPlanVO> ensureList(HttpSession session) {
        Object v = session.getAttribute(KEY_LIST);
        if (v == null) {
            List<DraftPlanVO> list = new ArrayList<>();
            session.setAttribute(KEY_LIST, list);
            return list;
        }
        return (List<DraftPlanVO>) v;
    }

    private DraftPlanVO ensureCurrent(HttpSession session) {
        Object v = session.getAttribute(KEY_CURRENT);
        if (v == null) {
            DraftPlanVO d = new DraftPlanVO();
            d.setName("작업중");
            d.setHall(0); d.setStudio(0); d.setDress(0); d.setMakeup(0);
            session.setAttribute(KEY_CURRENT, d);
            return d;
        }
        return (DraftPlanVO) v;
    }

    @Override
    public DraftPlanVO getCurrent(HttpSession session) {
        return ensureCurrent(session);
    }

    @Override
    public void addOrUpdateCurrent(HttpSession session, int category, int productNo) {
        DraftPlanVO cur = ensureCurrent(session);
        if (category == 0) cur.setHall(productNo);
        else if (category == 1) cur.setStudio(productNo);
        else if (category == 2) cur.setDress(productNo);
        else if (category == 3) cur.setMakeup(productNo);
        session.setAttribute(KEY_CURRENT, cur);
    }

    @Override
    public void saveCurrentAsDraft(HttpSession session, String name) {
        DraftPlanVO cur = ensureCurrent(session);
        if (name != null && !name.trim().isEmpty()) cur.setName(name.trim());

        DraftPlanVO copy = new DraftPlanVO();
        copy.setName(cur.getName());
        copy.setHall(cur.getHall());
        copy.setStudio(cur.getStudio());
        copy.setDress(cur.getDress());
        copy.setMakeup(cur.getMakeup());

        List<DraftPlanVO> list = ensureList(session);
        if (list.size() >= 20) list.remove(0); // 최대 20개 유지(요구사항 가이드)
        list.add(copy);
    }

    @Override
    public List<DraftPlanVO> getDrafts(HttpSession session) {
        return ensureList(session);
    }

    @Override
    public DraftPlanVO getByIndex(HttpSession session, int index) {
        List<DraftPlanVO> list = ensureList(session);
        if (index >= 0 && index < list.size()) return list.get(index);
        return null;
    }

    @Override
    public void removeByIndex(HttpSession session, int index) {
        List<DraftPlanVO> list = ensureList(session);
        if (index >= 0 && index < list.size()) list.remove(index);
    }

    @Override
    public void clearCurrent(HttpSession session) {
        session.removeAttribute(KEY_CURRENT);
    }
}
