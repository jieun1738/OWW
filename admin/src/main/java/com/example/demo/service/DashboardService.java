// src/main/java/com/example/demo/service/DashboardService.java
package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.mapper.DashboardMapper;
import com.example.demo.vo.ContractAggVO;
import com.example.demo.vo.DashboardKpiVO;
import com.example.demo.vo.MonthlyPlansVO;
import com.example.demo.vo.MonthlySalesVO;

@Service
public class DashboardService {

    private final DashboardMapper mapper;

    public DashboardService(DashboardMapper mapper) {
        this.mapper = mapper;
    }

    /** 컨트롤러에서 호출: 모델 채워 넣기 */
    public void fillModel(Model model) {
        // 기간 계산
        LocalDate now = LocalDate.now();
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate nextMonthStart = monthStart.plusMonths(1);
        LocalDate prevMonthStart = monthStart.minusMonths(1);

        // KPI
        long salesThisMonth = mapper.salesBetween(Date.valueOf(monthStart), Date.valueOf(nextMonthStart));
        long salesPrevMonth = mapper.salesBetween(Date.valueOf(prevMonthStart), Date.valueOf(monthStart));
        String salesDelta = calcDeltaText(salesPrevMonth, salesThisMonth);

        int newUsers = mapper.newUsersBetween(Date.valueOf(monthStart), Date.valueOf(nextMonthStart));
        int activeProducts = mapper.activeProducts();
        int openPlans = mapper.openPlans();

        DashboardKpiVO kpi = DashboardKpiVO.builder()
                .salesThisMonth(salesThisMonth)
                .salesMoMDeltaText(salesDelta)
                .newUsers(newUsers)
                .activeProducts(activeProducts)
                .openPlans(openPlans)
                .openPlansDeltaText("+0%") // 필요하면 이전 스냅샷 로직 추가
                .build();
        model.addAttribute("kpi", kpi);

        // 월별 범위: 최근 6개월
        LocalDate chartStart = monthStart.minusMonths(5); // 포함
        List<String> months = buildMonths(chartStart, nextMonthStart);

        // 시계열 조회
        List<MonthlySalesVO> salesSeries = mapper.monthlySales(Date.valueOf(chartStart), Date.valueOf(nextMonthStart));
        List<MonthlyPlansVO> planSeries  = mapper.monthlyPlans(Date.valueOf(chartStart), Date.valueOf(nextMonthStart));

        Map<String, Long> salesMap = new LinkedHashMap<>();
        for (MonthlySalesVO s : salesSeries) {
			salesMap.put(s.getYm(), s.getAmount());
		}
        Map<String, Integer> plansMap = new LinkedHashMap<>();
        for (MonthlyPlansVO p : planSeries) {
			plansMap.put(p.getYm(), p.getCnt());
		}

        List<Long> sales = new ArrayList<>();
        List<Integer> plans = new ArrayList<>();
        for (String ym : months) {
            sales.add(salesMap.getOrDefault(ym, 0L));
            plans.add(plansMap.getOrDefault(ym, 0));
        }

        // 계약 집계
        ContractAggVO agg = mapper.contractAgg();
        List<Integer> contractDone = Arrays.asList(
                agg.getDoneHall(), agg.getDoneStud(), agg.getDoneDres(), agg.getDoneMake()
        );
        List<Integer> contractPending = Arrays.asList(
                agg.getPendingHall(), agg.getPendingStud(), agg.getPendingDres(), agg.getPendingMake()
        );

        Map<String,Object> chart = new HashMap<>();
        chart.put("months", months);
        chart.put("sales", sales);
        chart.put("plans", plans);
        chart.put("contract", Map.of("done", contractDone, "pending", contractPending));
        model.addAttribute("chart", chart);

        // 위젯
        model.addAttribute("recentPlans", mapper.recentPlans(10));
        model.addAttribute("topProducts", mapper.topProducts(5));
    }

    private static String calcDeltaText(long prev, long curr) {
        if (prev <= 0) {
			return curr > 0 ? "+100%" : "+0%";
		}
        double rate = ((double) (curr - prev) / prev) * 100.0;
        long r = Math.round(rate);
        return (r >= 0 ? "+" : "") + r + "%";
    }

    private static List<String> buildMonths(LocalDate startInclusive, LocalDate endExclusive) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        List<String> list = new ArrayList<>();
        LocalDate d = startInclusive.withDayOfMonth(1);
        while (d.isBefore(endExclusive)) {
            list.add(d.format(fmt));
            d = d.plusMonths(1);
        }
        return list;
    }
}
