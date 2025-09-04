package com.wedding.oww.service;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wedding.oww.mapper.ProductMapper;
import com.wedding.oww.vo.DraftItemDTO;
import com.wedding.oww.vo.DraftPlanDTO;
import com.wedding.oww.vo.ProductVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DraftPlanServiceImpl implements DraftPlanService {

    private static final String ATTR_CURRENT = "planner.currentDraft";
    private static final String ATTR_VAULT   = "planner.draftVault";
    private static final int    MAX_VAULT    = 20;

    private final ProductMapper productMapper;

    @Override
    public DraftPlanDTO current(HttpSession session, String userEmail) {
        DraftPlanDTO d = (DraftPlanDTO) session.getAttribute(ATTR_CURRENT);
        if (d == null) {
            d = new DraftPlanDTO();
            d.setDraftId(UUID.randomUUID().toString());
            d.setUserEmail(userEmail);
            d.setCreatedAt(Instant.now());
            session.setAttribute(ATTR_CURRENT, d);
        }
        return d;
    }

    @Override
    public void newDraft(HttpSession session, String userEmail) {
        DraftPlanDTO d = new DraftPlanDTO();
        d.setDraftId(UUID.randomUUID().toString());
        d.setUserEmail(userEmail);
        d.setCreatedAt(Instant.now());
        session.setAttribute(ATTR_CURRENT, d);
    }

    @Override
    public void reset(HttpSession session) {
        DraftPlanDTO d = (DraftPlanDTO) session.getAttribute(ATTR_CURRENT);
        if (d != null) {
            d.setHall(null); d.setStudio(null); d.setDress(null); d.setMakeup(null);
        }
    }

    @Override
    public void addItem(HttpSession session, String userEmail, long productNo) {
        DraftPlanDTO d = current(session, userEmail);
        ProductVO p = productMapper.findById(productNo);
        if (p == null) return;

        switch (p.getCategory()) {
            case 0 -> d.setHall(productNo);
            case 1 -> d.setStudio(productNo);
            case 2 -> d.setDress(productNo);
            case 3 -> d.setMakeup(productNo);
            default -> { /* ignore */ }
        }
        session.setAttribute(ATTR_CURRENT, d);
    }

    @Override
    public void removeItem(HttpSession session, int category) {
        DraftPlanDTO d = (DraftPlanDTO) session.getAttribute(ATTR_CURRENT);
        if (d == null) return;
        switch (category) {
            case 0 -> d.setHall(null);
            case 1 -> d.setStudio(null);
            case 2 -> d.setDress(null);
            case 3 -> d.setMakeup(null);
            default -> { /* ignore */ }
        }
    }

    @Override
    public List<DraftItemDTO> selectedItems(HttpSession session) {
        DraftPlanDTO d = (DraftPlanDTO) session.getAttribute(ATTR_CURRENT);
        if (d == null) return List.of();

        List<Long> ids = new ArrayList<>();
        if (d.getHall()   != null) ids.add(d.getHall());
        if (d.getStudio() != null) ids.add(d.getStudio());
        if (d.getDress()  != null) ids.add(d.getDress());
        if (d.getMakeup() != null) ids.add(d.getMakeup());

        return ids.stream().map(productMapper::findById).filter(Objects::nonNull).map(p -> {
            DraftItemDTO it = new DraftItemDTO();
            it.setCategory(p.getCategory());
            it.setCategoryName(labelOf(p.getCategory()));
            it.setProductNo(p.getProductNo());
            it.setProductName(p.getProductName());
            it.setCost(p.getCost());
            it.setImg(p.getImg());
            return it;
        }).collect(Collectors.toList());
    }

    private String labelOf(Integer cat) {
        if (cat == null) return "";
        return switch (cat) {
            case 0 -> "웨딩홀";
            case 1 -> "스튜디오";
            case 2 -> "드레스";
            case 3 -> "메이크업";
            default -> "";
        };
    }

    @Override
    public int selectedTotal(HttpSession session) {
        return selectedItems(session).stream().map(DraftItemDTO::getCost)
                .filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
    }

    @Override
    public boolean isBudgetOk(HttpSession session) {
        // Planner 단계에서는 예산 사용 안 함 → 항상 true
        return true;
    }

    @Override
    public void saveToVault(HttpSession session) {
        DraftPlanDTO d = (DraftPlanDTO) session.getAttribute(ATTR_CURRENT);
        if (d == null) return;

        @SuppressWarnings("unchecked")
        Deque<DraftPlanDTO> vault = (Deque<DraftPlanDTO>) session.getAttribute(ATTR_VAULT);
        if (vault == null) vault = new ArrayDeque<>();

        DraftPlanDTO copy = new DraftPlanDTO();
        copy.setDraftId(UUID.randomUUID().toString());
        copy.setUserEmail(d.getUserEmail());
        copy.setTitle(d.getTitle());
        copy.setHall(d.getHall());
        copy.setStudio(d.getStudio());
        copy.setDress(d.getDress());
        copy.setMakeup(d.getMakeup());
        copy.setCreatedAt(Instant.now());

        vault.addFirst(copy);
        while (vault.size() > MAX_VAULT) vault.removeLast();
        session.setAttribute(ATTR_VAULT, vault);
    }

    @Override
    public void loadFromVault(HttpSession session) {
        @SuppressWarnings("unchecked")
        Deque<DraftPlanDTO> vault = (Deque<DraftPlanDTO>) session.getAttribute(ATTR_VAULT);
        if (vault == null || vault.isEmpty()) return;
        DraftPlanDTO top = vault.peekFirst();
        session.setAttribute(ATTR_CURRENT, top);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DraftPlanDTO> listVault(HttpSession session) {
        Deque<DraftPlanDTO> vault = (Deque<DraftPlanDTO>) session.getAttribute(ATTR_VAULT);
        if (vault == null) return List.of();
        return new ArrayList<>(vault);
    }
}
