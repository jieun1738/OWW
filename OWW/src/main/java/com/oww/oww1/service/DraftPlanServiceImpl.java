package com.oww.oww1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.PlanMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * - DB 접근은 ProductService/PlanMapper
 * - 선택 상태만 메모리(Map)와 세션으로 관리
 */
@Service
@RequiredArgsConstructor
public class DraftPlanServiceImpl implements DraftPlanService {

    private final ProductService productService; // DB에서 상품 1건/목록을 읽어옴
    private final PlanMapper planMapper;         // 최종 확정 시 DB INSERT

    /** 유저별 임시 상태(메모리) */
    private final Map<String, DraftState> store = new ConcurrentHashMap<>();

    /** 내부 상태 객체 */
    private static class DraftState {
        int seq = 1;                 // 플랜 번호 시퀀스
        int currentPlanNo = 1;       // 현재 활성 플랜 번호
        final Map<Integer, Slot> plans = new ConcurrentHashMap<>();

        DraftState() {
            plans.put(1, new Slot()); // 최초 1번 플랜 생성
        }
    }

    /** 선택 슬롯(카테고리별 선택된 product_no: 없으면 0) */
    private static class Slot {
        int hall = 0;    // 0
        int studio = 0;  // 1
        int dress = 0;   // 2
        int makeup = 0;  // 3

        void set(int category, int productNo) {
            switch (category) {
                case 0: hall = productNo; break;
                case 1: studio = productNo; break;
                case 2: dress = productNo; break;
                case 3: makeup = productNo; break;
                default: /* ignore */ ;
            }
        }

        int get(int category) {
            switch (category) {
                case 0: return hall;
                case 1: return studio;
                case 2: return dress;
                case 3: return makeup;
                default: return 0;
            }
        }

        void reset() {
            hall = studio = dress = makeup = 0;
        }
    }

    // ---- 내부 유틸(메모리) ----
    private DraftState stateOf(String userEmail) {
        return store.computeIfAbsent(userEmail, k -> new DraftState());
    }
    private Slot currentSlot(String userEmail) {
        DraftState st = stateOf(userEmail);
        return st.plans.get(st.currentPlanNo); // 항상 존재
    }

    // =========================
    // 인터페이스 구현(메모리 파트)
    // =========================
    @Override
    public PlanVO getOrCreate(String userEmail) {
        DraftState st = stateOf(userEmail);
        PlanVO vo = new PlanVO();
        vo.setPlan_no(st.currentPlanNo); // 화면에 번호만 쓰므로 이것만 채워도 충분
        return vo;
    }

    @Override
    public List<ProductVO> getSelectedProducts(String userEmail) {
        Slot s = currentSlot(userEmail);
        List<ProductVO> list = new ArrayList<>(4);
        addIfExists(list, s.hall);
        addIfExists(list, s.studio);
        addIfExists(list, s.dress);
        addIfExists(list, s.makeup);
        return list;
    }

    private void addIfExists(List<ProductVO> list, int productNo) {
        if (productNo > 0) {
            ProductVO p = productService.getOne(productNo); // DB에서 읽음
            if (p != null) list.add(p);
        }
    }

    @Override
    public int getTotalCost(String userEmail) {
        List<ProductVO> sel = getSelectedProducts(userEmail);
        int sum = 0;
        if (sel != null) {
            for (ProductVO p : sel) {
                sum += (p != null ? p.getCost() : 0);
            }
        }
        return sum;
    }

    @Override
    public void select(String userEmail, int productNo, int category) {
        Slot s = currentSlot(userEmail);
        s.set(category, productNo);
    }

    @Override
    public void deleteOne(String userEmail, int category) {
        Slot s = currentSlot(userEmail);
        s.set(category, 0);
    }

    @Override
    public void reset(String userEmail) {
        currentSlot(userEmail).reset();
    }

    @Override
    public void addNewPlan(String userEmail) {
        DraftState st = stateOf(userEmail);
        int next = ++st.seq;
        st.plans.put(next, new Slot());
        st.currentPlanNo = next;
    }

    // =========================
    // 세션 기반: DIY 현재 선택(currentPlan)
    // =========================
    @Override
    public void setCurrentItem(HttpSession session, int category, int productNo) {
        Map<String, Object> cur = ensureCurrent(session);
        if (category == 0) cur.put("hall", productNo);
        else if (category == 1) cur.put("studio", productNo);
        else if (category == 2) cur.put("dress", productNo);
        else if (category == 3) cur.put("makeup", productNo);
        session.setAttribute("currentPlan", cur);
    }

    @Override
    public Map<String, Object> getCurrentPlan(HttpSession session) {
        Map<String, Object> cur = getMap(session, "currentPlan");
        if (cur == null) cur = ensureCurrent(session);
        return copyStringKeyMap(cur);
    }

    @Override
    public void resetCurrentPlan(HttpSession session) {
        Map<String, Object> blank = new HashMap<String, Object>();
        blank.put("hall", 0);
        blank.put("studio", 0);
        blank.put("dress", 0);
        blank.put("makeup", 0);
        session.setAttribute("currentPlan", blank);
    }

    // =========================
    // 세션 기반: 임시보관함(planBox)
    // =========================

    // 구형 시그니처 호환 (컨트롤러에서 session 안 넘기던 코드 방지)
    @Override
    public void saveCurrentToBox(String userEmail) {
        HttpSession session = currentSession();
        if (session == null) return;
        saveCurrentToBox(session, userEmail);
    }

    @Override
    public void saveCurrentToBox(HttpSession session, String userEmail) {
        Map<String, Object> cur = ensureCurrent(session);

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("type", "DIY");
        item.put("userEmail", userEmail);
        item.put("hall", toInt(cur.get("hall")));
        item.put("studio", toInt(cur.get("studio")));
        item.put("dress", toInt(cur.get("dress")));
        item.put("makeup", toInt(cur.get("makeup")));

        List<Map<String, Object>> box = ensureBox(session);
        box.add(item);
        session.setAttribute("planBox", box);
    }

    @Override
    public void saveConfirmContextToBox(HttpSession session, String userEmail) {
        Map<String, Object> ctx = getMap(session, "confirmContext");
        if (ctx == null) return;

        Map<String, Object> item = new HashMap<String, Object>();
        String type = toStr(ctx.get("type"));
        item.put("type", type.isEmpty() ? "DIY" : type);
        item.put("userEmail", userEmail);

        if ("PKG".equals(item.get("type"))) {
            item.put("packageNo", toInt(ctx.get("packageNo")));
        } else {
            item.put("hall",   toInt(ctx.get("hall")));
            item.put("studio", toInt(ctx.get("studio")));
            item.put("dress",  toInt(ctx.get("dress")));
            item.put("makeup", toInt(ctx.get("makeup")));
        }

        List<Map<String, Object>> box = ensureBox(session);
        box.add(item);
        session.setAttribute("planBox", box);

        // confirmContext는 사용 후 제거(선택)
        session.removeAttribute("confirmContext");
    }

    @Override
    public List<Map<String, Object>> getPlanBox(HttpSession session) {
        List<Map<String, Object>> src = ensureBox(session);
        // 방어적 복사(문자열 키 보장)
        List<Map<String, Object>> out = new ArrayList<Map<String, Object>>();
        for (Object o : src) {
            if (o instanceof Map<?, ?>) {
                out.add(copyStringKeyMap((Map<?, ?>) o));
            }
        }
        return out;
    }

    @Override
    public void removeFromBox(HttpSession session, int index) {
        List<Map<String, Object>> box = ensureBox(session);
        if (index >= 0 && index < box.size()) {
            box.remove(index);
            session.setAttribute("planBox", box);
        }
    }

    @Override
    public void clearPlanBox(HttpSession session) {
        session.setAttribute("planBox", new ArrayList<Map<String, Object>>());
    }

    // =========================
    // 최종 확정 (DB 저장 + 보관함 제거)
    // =========================
    @Override
    public void finalizeFromBox(String userEmail, int index, HttpSession session) {
        List<Map<String, Object>> box = ensureBox(session);
        if (index < 0 || index >= box.size()) return;

        Map<String, Object> item = toStringKeyMap(box.get(index));
        String type = toStr(item.get("type"));

        if ("PKG".equals(type)) {
            int packageNo = toInt(item.get("packageNo"));
            if (packageNo > 0) {
                planMapper.insertPlanFromPackage(userEmail, packageNo);
            }
        } else {
            int hall   = toInt(item.get("hall"));
            int studio = toInt(item.get("studio"));
            int dress  = toInt(item.get("dress"));
            int makeup = toInt(item.get("makeup"));
            planMapper.insertPlanDIY(userEmail, hall, studio, dress, makeup);
        }

        box.remove(index);
        session.setAttribute("planBox", box);
    }

    // =========================
    // 내부 유틸(세션)
    // =========================
    private Map<String, Object> ensureCurrent(HttpSession session) {
        Map<String, Object> cur = getMap(session, "currentPlan");
        if (cur == null) {
            cur = new HashMap<String, Object>();
            cur.put("hall", 0);
            cur.put("studio", 0);
            cur.put("dress", 0);
            cur.put("makeup", 0);
            session.setAttribute("currentPlan", cur);
        }
        return cur;
    }

    private List<Map<String, Object>> ensureBox(HttpSession session) {
        Object b = session.getAttribute("planBox");
        if (b instanceof List<?>) {
            List<?> src = (List<?>) b;
            ArrayList<Map<String, Object>> typed = new ArrayList<Map<String, Object>>();
            for (Object o : src) {
                if (o instanceof Map<?, ?>) {
                    typed.add(toStringKeyMap((Map<?, ?>) o));
                }
            }
            // 원본을 안전한 형태로 덮어써서 이후 캐스팅 불필요
            session.setAttribute("planBox", typed);
            return typed;
        }
        ArrayList<Map<String, Object>> init = new ArrayList<Map<String, Object>>();
        session.setAttribute("planBox", init);
        return init;
    }

    private Map<String, Object> getMap(HttpSession session, String key) {
        Object o = session.getAttribute(key);
        if (o instanceof Map<?, ?>) {
            return toStringKeyMap((Map<?, ?>) o);
        }
        return null;
    }

    private Map<String, Object> toStringKeyMap(Map<?, ?> src) {
        Map<String, Object> m = new HashMap<String, Object>();
        for (Map.Entry<?, ?> e : src.entrySet()) {
            String k = String.valueOf(e.getKey());
            m.put(k, e.getValue());
        }
        return m;
    }

    private Map<String, Object> copyStringKeyMap(Map<?, ?> src) {
        Map<String, Object> m = new HashMap<String, Object>();
        for (Map.Entry<?, ?> e : src.entrySet()) {
            String k = String.valueOf(e.getKey());
            m.put(k, e.getValue());
        }
        return m;
    }

    private int toInt(Object o) {
        if (o == null) return 0;
        try { return Integer.parseInt(String.valueOf(o)); }
        catch (Exception e) { return 0; }
    }

    private String toStr(Object o) {
        return (o == null) ? "" : String.valueOf(o);
    }

    private HttpSession currentSession() {
        try {
            ServletRequestAttributes a = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (a == null) return null;
            HttpServletRequest req = a.getRequest();
            if (req == null) return null;
            return req.getSession();
        } catch (Exception ignore) {
            return null;
        }
    }
}
