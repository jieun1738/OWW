package com.wedding.oww.service;

import java.util.List;

import com.wedding.oww.vo.DraftItemDTO;
import com.wedding.oww.vo.DraftPlanDTO;

import jakarta.servlet.http.HttpSession;

public interface DraftPlanService {
    DraftPlanDTO current(HttpSession session, String userEmail);
    void newDraft(HttpSession session, String userEmail);
    void reset(HttpSession session);
    void addItem(HttpSession session, String userEmail, long productNo);
    void removeItem(HttpSession session, int category);
    List<DraftItemDTO> selectedItems(HttpSession session);
    int selectedTotal(HttpSession session);
    boolean isBudgetOk(HttpSession session);
    void saveToVault(HttpSession session);
    void loadFromVault(HttpSession session);
    List<DraftPlanDTO> listVault(HttpSession session);
}
