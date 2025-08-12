package com.example.demo.service;

import com.example.demo.dto.ChartDto;
import com.example.demo.repo.SafeBoxRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SafeBoxService {

    private final SafeBoxRepository repo;

    public SafeBoxService(SafeBoxRepository repo) {
        this.repo = repo;
    }

    /** 목표/저축 정보 조회 */
    public GoalView getGoal(String email) {
        long targetAmount = nvl(repo.findTargetAmount(email));
        long savedAmount = nvl(repo.findSavedAmount(email));
        boolean regular = repo.isRegularSaving(email);

        return new GoalView(savedAmount, targetAmount, regular);
    }

    /** 카테고리별 차트 데이터 조회 */
    public ChartDto getCategoryChart(String email) {
        Map<String, long[]> map = repo.findCategoryBreakdown(email);
        List<String> labels = new ArrayList<>();
        List<Long> budget = new ArrayList<>();
        List<Long> paid = new ArrayList<>();

        if (map != null) {
            map.forEach((k, v) -> {
                labels.add(k);
                budget.add(v.length > 0 ? v[0] : 0L);
                paid.add(v.length > 1 ? v[1] : 0L);
            });
        }
        return new ChartDto(labels, budget, paid);
    }

    private long nvl(Long value) {
        return value != null ? value : 0L;
    }

    /** 목표/저축 정보를 담는 Record */
    public record GoalView(long savedAmount, long targetAmount, boolean regular) {}
}
