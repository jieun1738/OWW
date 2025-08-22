package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.DraftItemDTO;
import com.oww.oww1.VO.DraftPlanDTO;

import jakarta.servlet.http.HttpSession;

public interface DraftPlanService {
    DraftPlanDTO current(HttpSession session, String userEmail);
    void newDraft(HttpSession session, String userEmail);
    void reset(HttpSession session);
    void addItem(HttpSession session, String userEmail, int productNo);
    void removeItem(HttpSession session, int category);
    List<DraftItemDTO> selectedItems(HttpSession session);
    int selectedTotal(HttpSession session);
    boolean isBudgetOk(HttpSession session);
    void saveToVault(HttpSession session);
    void loadFromVault(HttpSession session);
    List<DraftPlanDTO> listVault(HttpSession session);
}
