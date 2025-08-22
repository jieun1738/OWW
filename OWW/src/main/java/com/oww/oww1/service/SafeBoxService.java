package com.oww.oww1.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oww.oww1.VO.ChartDto;
import com.oww.oww1.mapper.SafeBoxMapper;
import com.oww.oww1.VO.GoalDto;
import com.oww.oww1.VO.PeriodAmt;

@Service
public class SafeBoxService {

    private final SafeBoxMapper mapper;

    public SafeBoxService(SafeBoxMapper mapper) {
        this.mapper = mapper;
    }

    /** 목표 요약 */
    @Transactional(readOnly = true)
    public GoalDto getGoal(String email) {
        long target = mapper.findTargetAmount(email).orElse(0L);
        long saved  = mapper.sumSaved(email);
        return new GoalDto(target, saved);
    }

    /** 목표 저장/갱신 */
    @Transactional
    public void upsertGoal(String email, long targetAmount) {
        mapper.upsertTargetAmount(email, targetAmount);
    }

    /** 저축 내역 추가 */
    @Transactional
    public void addSaving(String email, long amount, String memo) {
        mapper.insertSaving(email, amount, memo);
    }

    /** 일별 차트 */
    @Transactional(readOnly = true)
    public ChartDto getDailyChart(String email) {
        YearMonth ym = YearMonth.now();
        int days = ym.lengthOfMonth();

        long target = mapper.findTargetAmount(email).orElse(0L);

        // 라벨: 1일 ~ 말일
        List<String> labels = new ArrayList<>();
        for (int i = 1; i <= days; i++) labels.add(i + "일");

        // DB에서 가져온 데이터
        List<PeriodAmt> rows = mapper.selectDailyThisMonth(email);
        Map<String, Long> paidMap = new HashMap<>();
        if (rows != null) rows.forEach(r -> paidMap.put(r.getPeriod(), r.getAmount()));

        // 실적 매핑 (날짜 비교를 위해 yyyy-MM-dd 문자열 생성)
        List<Long> paid = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            String key = ym.atDay(i).toString(); // ex) 2025-08-18
            paid.add(paidMap.getOrDefault(key, 0L));
        }

        long perDay = perUnit(target, days);
        List<Long> budget = Collections.nCopies(days, perDay);

        return new ChartDto(labels, budget, paid);
    }

    /** 월별 차트 */
    @Transactional(readOnly = true)
    public ChartDto getMonthlyChart(String email) {
        YearMonth now = YearMonth.now();
        YearMonth start = now.minusMonths(11);

        long target = mapper.findTargetAmount(email).orElse(0L);

        // 라벨: 최근 12개월
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            labels.add(start.plusMonths(i).getMonthValue() + "월");
        }

        // DB에서 가져온 데이터
        List<PeriodAmt> rows = mapper.selectMonthly12(email);
        Map<String, Long> paidMap = new HashMap<>();
        if (rows != null) rows.forEach(r -> paidMap.put(r.getPeriod(), r.getAmount()));

        // 실적 매핑 (yyyy-MM 형태)
        List<Long> paid = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String key = start.plusMonths(i).toString(); // ex) 2025-08
            paid.add(paidMap.getOrDefault(key, 0L));
        }

        long perMonth = perUnit(target, 12);
        List<Long> budget = Collections.nCopies(12, perMonth);

        return new ChartDto(labels, budget, paid);
    }

    /** 총액을 n 구간으로 균등 분할 */
    private long perUnit(long target, int n) {
        if (target <= 0 || n <= 0) return 0L;
        return Math.round((double) target / n);
    }
}
