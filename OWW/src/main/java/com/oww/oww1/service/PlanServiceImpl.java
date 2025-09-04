package com.oww.oww1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.PlanMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;
    private final HttpSession session;

    @Override
    public List<PlanVO> listFinalPlans(String userEmail) {
        return planMapper.findPlansByUser(userEmail);
    }

    @Override
    public List<ProductVO> listProductsOfPlan(int planNo) {
        return planMapper.findProductsByPlanNo(planNo);
    }

    @Override
    public int finalizeDIY(PlanVO vo) {
        // Mapper의 실제 시그니처(다중 파라미터)에 맞춰 호출합니다.
        return planMapper.insertPlanDIY(
                vo.getUserEmail(),
                vo.getHall(),
                vo.getStudio(),
                vo.getDress(),
                vo.getMakeup()
        );
    }

    @Override
    public void finalizeFromTempBox(String userEmail, int index) {
        Object attr = session.getAttribute("planBox");
        if (!(attr instanceof List<?>)) return;

        List<?> src = (List<?>) attr;
        if (index < 0 || index >= src.size()) return;

        Object row = src.get(index);
        if (!(row instanceof Map<?, ?>)) return;

        Map<?, ?> raw = (Map<?, ?>) row;
        Map<String, Object> item = toStringKeyMap(raw);
        String type = String.valueOf(item.getOrDefault("type", "DIY"));

        if ("PKG".equals(type)) {
            int packageNo = parseInt(item.get("packageNo"));
            if (packageNo > 0) {
                planMapper.insertPlanFromPackage(userEmail, packageNo);
            }
        } else {
            int hall   = parseInt(item.get("hall"));
            int studio = parseInt(item.get("studio"));
            int dress  = parseInt(item.get("dress"));
            int makeup = parseInt(item.get("makeup"));
            planMapper.insertPlanDIY(userEmail, hall, studio, dress, makeup);
        }

        // 커밋한 항목 제거
        List<Map<String, Object>> newBox = new ArrayList<>();
        for (Object o : src) {
            if (o instanceof Map<?, ?>) {
                newBox.add(toStringKeyMap((Map<?, ?>) o));
            }
        }
        newBox.remove(index);
        session.setAttribute("planBox", newBox);
    }

    // ===== 내부 유틸 =====
    private Map<String, Object> toStringKeyMap(Map<?, ?> src) {
        Map<String, Object> m = new HashMap<>();
        for (Map.Entry<?, ?> e : src.entrySet()) {
            m.put(String.valueOf(e.getKey()), e.getValue());
        }
        return m;
    }

    private int parseInt(Object o) {
        if (o == null) return 0;
        try { return Integer.parseInt(String.valueOf(o)); }
        catch (Exception e) { return 0; }
    }
}
