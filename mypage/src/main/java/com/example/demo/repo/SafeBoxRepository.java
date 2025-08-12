package com.example.demo.repo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SafeBoxRepository {

    // 목표 금액 조회 (DB 연결 시 실제 SQL로 교체)
    public Long findTargetAmount(String email) {
        return 1000000L; // 예시값
    }

    // 저축 금액 조회
    public Long findSavedAmount(String email) {
        return 250000L; // 예시값
    }

    // 정기저축 여부
    public boolean isRegularSaving(String email) {
        return true; // 예시값
    }

    // 카테고리별 예산/지출 데이터
    public Map<String, long[]> findCategoryBreakdown(String email) {
        Map<String, long[]> map = new HashMap<>();
        map.put("식비", new long[]{500000L, 300000L});
        map.put("교통", new long[]{200000L, 150000L});
        return map;
    }
}
