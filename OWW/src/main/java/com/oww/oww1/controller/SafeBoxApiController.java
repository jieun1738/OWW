package com.oww.oww1.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oww.oww1.VO.BudgetForm;
import com.oww.oww1.VO.ChartDto;
import com.oww.oww1.VO.GoalDto;
import com.oww.oww1.service.BudgetService;
import com.oww.oww1.service.SafeBoxService;

@RestController
@RequestMapping("/api/safe_box")
public class SafeBoxApiController {

    private final SafeBoxService safeBoxService;
    private final BudgetService budgetService;

    private static final String FALLBACK_EMAIL = "user1@example.com";

    public SafeBoxApiController(SafeBoxService safeBoxService,
                                BudgetService budgetService) {
        this.safeBoxService = safeBoxService;
        this.budgetService  = budgetService;
    }

    /** 목표/저축 요약 + 사이드바 예산 합계 */
    @GetMapping("/goal")
    public ResponseEntity<Map<String, Object>> getGoal(Principal principal) {
        String email = emailOf(principal);
        GoalDto goal = safeBoxService.getGoal(email);

        long target = (goal != null) ? goal.targetAmount() : 0L;
        long saved  = (goal != null) ? goal.savedAmount()  : 0L;
        long remain = Math.max(target - saved, 0L);
        int progress = (target > 0)
                ? (int) Math.min(100, Math.round(saved * 100.0 / target))
                : 0;

        // 사이드바 예산 합계
        BudgetForm budget = budgetService.getBudget(email);
        long total = 0L;
        if (budget != null) {
            total = safeSum(budget.getWeddingHall())
                  + safeSum(budget.getStudio())
                  + safeSum(budget.getDress())
                  + safeSum(budget.getMakeup());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("targetAmount", target);
        result.put("savedAmount", saved);
        result.put("remainAmount", remain);
        result.put("progressPercent", progress);
        result.put("sidebarTotal", total);  // 사이드바 총 예산

        return ResponseEntity.ok(result);
    }

    /** 목표금액 저장/수정 (form/json 모두 지원) */
    @PostMapping("/goal")
    public ResponseEntity<?> saveGoal(@RequestParam(name = "targetAmount", required = false) Long targetAmount,
                                      @RequestBody(required = false) Map<String, Object> body,
                                      Principal principal) {

        if (targetAmount == null && body != null) {
            targetAmount = asLong(body.get("targetAmount"));
        }
        if (targetAmount == null || targetAmount < 0L) {
            return ResponseEntity.badRequest()
                    .body(Map.of("ok", false, "msg", "targetAmount는 0 이상 정수여야 합니다."));
        }

        safeBoxService.upsertGoal(emailOf(principal), targetAmount);
        return ResponseEntity.ok(Map.of("ok", true));
    }

    /** 저축 로그 추가 (form/json 모두 지원) */
    @PostMapping("/save")
    public ResponseEntity<?> addSaving(@RequestParam(name = "amount", required = false) Long amount,
                                       @RequestParam(name = "memo", required = false) String memo,
                                       @RequestBody(required = false) Map<String, Object> body,
                                       Principal principal) {

        if (amount == null && body != null) {
            amount = asLong(body.get("amount"));
            if (memo == null && body.get("memo") instanceof String s) memo = s;
        }
        if (amount == null || amount <= 0L) {
            return ResponseEntity.badRequest()
                    .body(Map.of("ok", false, "msg", "amount는 1 이상 정수여야 합니다."));
        }

        safeBoxService.addSaving(emailOf(principal), amount, memo);
        return ResponseEntity.ok(Map.of("ok", true));
    }

    /** 차트 데이터 (range=daily|monthly) */
    @GetMapping("/chart")
    public ResponseEntity<ChartDto> chart(@RequestParam(defaultValue = "daily") String range,
                                          Principal principal) {
        String email = emailOf(principal);
        ChartDto dto = "monthly".equalsIgnoreCase(range)
                ? safeBoxService.getMonthlyChart(email)
                : safeBoxService.getDailyChart(email);
        return ResponseEntity.ok(dto);
    }

    // ===== helpers =====
    private String emailOf(Principal principal) {
        return (principal != null && principal.getName() != null && !principal.getName().isBlank())
                ? principal.getName()
                : FALLBACK_EMAIL;
    }

    /** 숫자(문자열 포함)를 Long으로 안전 파싱 */
    private Long asLong(Object v) {
        if (v == null) return null;
        if (v instanceof Number n) return n.longValue();
        if (v instanceof String s) {
            String t = s.replaceAll("[_,\\s]", "");
            if (t.isEmpty()) return null;
            try { return Long.parseLong(t); } catch (NumberFormatException ignore) { return null; }
        }
        return null;
    }

    private long safeSum(Long v) { return (v != null) ? v : 0L; }
}
