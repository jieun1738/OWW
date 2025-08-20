package com.wedding.oww.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wedding.oww.service.DraftPlanService;
import com.wedding.oww.service.PlanCompareService;
import com.wedding.oww.service.PlannerService;
import com.wedding.oww.service.ProductCompareService;
import com.wedding.oww.vo.DraftPlanDTO;
import com.wedding.oww.vo.PackageVO;
import com.wedding.oww.vo.PlanVO;
import com.wedding.oww.vo.ProductVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/planner")
public class WeddingPlannerController {

    private final PlannerService plannerService;
    private final DraftPlanService draftPlanService;
    private final ProductCompareService productCompareService;
    private final PlanCompareService planCompareService;

    private String userEmail(HttpServletRequest req) {
        Object v = req.getAttribute("userEmail");
        return v == null ? null : v.toString();
    }

    /** 공통 사이드바 통계 값 주입 */
    @ModelAttribute
    public void sidebarCommon(HttpServletRequest req, HttpSession session, Model model) {
        try {
            int draftCount = draftPlanService.listVault(session).size();
            int productCompareCount = productCompareService.size(session);
            int planCompareCount = planCompareService.size(session);

            model.addAttribute("draftCount", draftCount);
            model.addAttribute("productCompareCount", productCompareCount);
            model.addAttribute("planCompareCount", planCompareCount);

            // progressPercent를 개별 페이지에서 안 넘겨도 0으로 방어
            if (!model.containsAttribute("progressPercent")) {
                model.addAttribute("progressPercent", 0);
            }
        } catch (Exception ignore) {
            // 세션 미존재 등 예외는 무시 (초기 로딩 방어)
        }
    }

    /* ------- Info / Intro / Category ------- */

    @GetMapping("/info")
    public String info(HttpServletRequest req, HttpSession session, Model model) {
        String email = userEmail(req);
        int sessionDraftCount = draftPlanService.listVault(session).size();
        int committedPlanCount = StringUtils.hasText(email) ? plannerService.getCommittedPlanCount(email) : 0;
        List<PackageVO> recommended = plannerService.getPackages(null).stream().limit(3).toList();

        model.addAttribute("sessionDraftCount", sessionDraftCount);
        model.addAttribute("committedPlanCount", committedPlanCount);
        model.addAttribute("recommendedPackages", recommended);
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlannerInfo";
    }

    @GetMapping("/intro")
    public String intro(Model model) {
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlannerIntro";
    }

    @GetMapping("/category")
    public String category(@RequestParam(required = false) Integer type, Model model) {
        model.addAttribute("pkgPreviews", plannerService.getPackagePreviews(type));
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingCategory";
    }

    /* --------------- DIY ------------------- */

    @GetMapping("/diy")
    public String diy(
            @RequestParam(required = false, defaultValue = "false") boolean all,
            @RequestParam(required = false) Integer cat,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            HttpServletRequest req, HttpSession session, Model model
    ) {
        Integer category = all ? null : (cat == null ? 1 : cat); // 기본 스튜디오
        List<ProductVO> products = plannerService.getProducts(category, q, sort);

        model.addAttribute("products", products);
        model.addAttribute("all", all);
        model.addAttribute("cat", category);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);

        model.addAttribute("selectedPlan", draftPlanService.current(session, userEmail(req)));
        model.addAttribute("selectedItems", draftPlanService.selectedItems(session));
        model.addAttribute("selectedTotal", draftPlanService.selectedTotal(session));
        model.addAttribute("isBudgetOk", draftPlanService.isBudgetOk(session));
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingDIY";
    }

    // Draft 조작
    @PostMapping("/draft/new")
    public String draftNew(HttpServletRequest req, HttpSession session) {
        draftPlanService.newDraft(session, userEmail(req));
        return "redirect:/planner/diy";
    }

    @PostMapping("/draft/reset")
    public String draftReset(HttpSession session) {
        draftPlanService.reset(session);
        return "redirect:/planner/diy";
    }

    @PostMapping("/draft/item")
    public String draftAddItem(@RequestParam long productNo, HttpServletRequest req, HttpSession session) {
        draftPlanService.addItem(session, userEmail(req), productNo);
        return "redirect:/planner/diy";
    }

    @PostMapping("/draft/item/delete")
    public String draftDeleteItem(@RequestParam int category, HttpSession session) {
        draftPlanService.removeItem(session, category);
        return "redirect:/planner/diy";
    }

    @PostMapping("/draft/save")
    public String draftSave(HttpSession session) {
        draftPlanService.saveToVault(session);
        return "redirect:/planner/diy";
    }

    @PostMapping("/draft/load")
    public String draftLoad(HttpSession session) {
        draftPlanService.loadFromVault(session);
        return "redirect:/planner/diy";
    }

    /* --------------- 비교(제품) ------------------- */

    @PostMapping("/compare/product/add")
    public String compareProductAdd(@RequestParam long productNo, HttpSession session) {
        productCompareService.add(session, productNo);
        return "redirect:/planner/diy";
    }

    @PostMapping("/compare/product/remove")
    public String compareProductRemove(@RequestParam long productNo, HttpSession session) {
        productCompareService.remove(session, productNo);
        return "redirect:/planner/compare/product";
    }

    @PostMapping("/compare/product/reset")
    public String compareProductReset(HttpSession session) {
        productCompareService.reset(session);
        return "redirect:/planner/compare/product";
    }

    @GetMapping("/compare/product")
    public String compareProductView(HttpSession session, Model model) {
        model.addAttribute("products", productCompareService.list(session));
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingProductCompare";
    }

    /* --------------- 비교(플랜) ------------------- */

    @PostMapping("/compare/plan/toggle")
    public String comparePlanToggle(@RequestParam long planNo, HttpSession session) {
        planCompareService.toggle(session, planNo);
        return "redirect:/planner/plan/list";
    }

    @PostMapping("/compare/plan/reset")
    public String comparePlanReset(HttpSession session) {
        planCompareService.reset(session);
        return "redirect:/planner/compare/plan";
    }

    @GetMapping("/compare/plan")
    public String comparePlanView(HttpSession session, Model model) {
        List<PlanVO> plans = planCompareService.list(session);
        model.addAttribute("plans", plans);
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlanCompare";
    }

    /* --------------- 확정 화면/확정 ------------------- */

    @GetMapping("/confirm")
    public String confirmView(
            @RequestParam(required = false) Long packageNo,
            HttpServletRequest req, HttpSession session, Model model
    ) {
        boolean isPackage = (packageNo != null);
        List<Long> ids = new ArrayList<>();
        int discountPercent = 0;

        if (isPackage) {
            PackageVO pkg = plannerService.getPackage(packageNo);
            if (pkg == null) return "redirect:/planner/category";
            ids.add(pkg.getHall()); ids.add(pkg.getStudio()); ids.add(pkg.getDress()); ids.add(pkg.getMakeup());
            discountPercent = (pkg.getDiscount() == null ? 0 : pkg.getDiscount());
            model.addAttribute("packageNo", packageNo);
        } else {
            String email = userEmail(req);
            DraftPlanDTO d = draftPlanService.current(session, email);
            if (d.getHall() != null) ids.add(d.getHall());
            if (d.getStudio() != null) ids.add(d.getStudio());
            if (d.getDress() != null) ids.add(d.getDress());
            if (d.getMakeup() != null) ids.add(d.getMakeup());
        }

        List<ProductVO> items = plannerService.getProductsByIds(ids);
        int total = items.stream().map(ProductVO::getCost).filter(v -> v != null).mapToInt(Integer::intValue).sum();
        int finalTotal = total - (total * discountPercent / 100);
        boolean canConfirm = !items.isEmpty();

        model.addAttribute("isPackage", isPackage);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("discountPercent", discountPercent);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("canConfirm", canConfirm);
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingConfirm";
    }

    @PostMapping("/confirm")
    public String confirmDIY(HttpServletRequest req, HttpSession session) {
        String email = userEmail(req);
        DraftPlanDTO d = draftPlanService.current(session, email);
        if (email == null || d == null) return "redirect:/planner/diy";
        plannerService.confirmDIY(email, d.getHall(), d.getStudio(), d.getDress(), d.getMakeup());
        draftPlanService.newDraft(session, email);
        return "redirect:/planner/plan/list";
    }

    @PostMapping("/package/confirm")
    public String confirmPackage(@RequestParam long packageNo, HttpServletRequest req, HttpSession session) {
        String email = userEmail(req);
        if (email == null) return "redirect:/planner/info";
        plannerService.confirmPackage(email, packageNo);
        draftPlanService.newDraft(session, email);
        return "redirect:/planner/plan/list";
    }

    /* --------------- 확정 목록 ------------------- */

    @GetMapping("/plan/list")
    public String planList(HttpServletRequest req, HttpSession session, Model model) {
        String email = userEmail(req);
        if (email == null) return "redirect:/planner/info";
        model.addAttribute("planCards", plannerService.getPlanCards(email)); // ★ 카드용
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlanList";
    }
}
