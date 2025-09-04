package com.oww.oww1.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oww.oww1.VO.PackageCardVO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.service.DraftPlanService;
import com.oww.oww1.service.PackageService;
import com.oww.oww1.service.PlanService;
import com.oww.oww1.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WeddingPlannerController {

    private final ProductService productService;
    private final DraftPlanService draft;
    private final PlanService planService;
    private final PackageService packageService;

    /* -------------------------- 공통 유틸 -------------------------- */
    private String ensureUser(HttpSession session){
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isBlank()){
            email = "user1@example.com"; // 샘플 계정
            session.setAttribute("userEmail", email);
        }
        return email;
    }
    private String enc(String s){
        return (s == null) ? "" : URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
    private String toRegexOr(String ex){
        if (ex == null || ex.isBlank()) return null;
        String[] toks = ex.toLowerCase().split("[,\\s]+");
        StringBuilder sb = new StringBuilder();
        for (String t : toks){
            if (t.isBlank()) continue;
            String esc = t.replaceAll("([\\\\.^$|?*+()\\[\\]{}])", "\\\\$1");
            if (sb.length() > 0) sb.append("|");
            sb.append(esc);
        }
        return sb.length() == 0 ? null : sb.toString();
    }
    private String backTo(HttpServletRequest req, String fallback){
        String ref = req.getHeader("Referer");
        if (ref != null && !ref.isBlank()) return "redirect:" + ref;
        return "redirect:" + fallback;
    }

    /* -------------------------- 단순 뷰 매핑 -------------------------- */

    @GetMapping("/WeddingPlanner/WeddingPlannerInfo")
    public String weddingPlannerInfo(Model model, HttpSession session) { 
    	
    	@SuppressWarnings("unchecked")
        List<ProductVO> compareList = (List<ProductVO>) session.getAttribute("productCompareList");
        int compareCnt = (compareList == null) ? 0 : compareList.size();
    	
    	model.addAttribute("productCompareCount", compareCnt);
    	return "WeddingPlanner/WeddingPlannerInfo"; 
    	}

    @GetMapping("/WeddingPlanner/WeddingPlannerIntro")
    public String weddingPlannerIntro(Model model, HttpSession session) { 
    	
    	@SuppressWarnings("unchecked")
        List<ProductVO> compareList = (List<ProductVO>) session.getAttribute("productCompareList");
        int compareCnt = (compareList == null) ? 0 : compareList.size();
    	
    	model.addAttribute("productCompareCount", compareCnt);
    	return "WeddingPlanner/WeddingPlannerIntro"; 	
    }

    /* ====================== 임시 플랜 보관함 ====================== */
    @GetMapping("/planner/WeddingPlanList")
    public String weddingPlanList(Model model, HttpSession session){
        String userEmail = ensureUser(session);

        @SuppressWarnings("unchecked")
        List<List<ProductVO>> boxDetails = (List<List<ProductVO>>) session.getAttribute("planBoxDetails");
        if (boxDetails == null) boxDetails = new ArrayList<>();

        List<PlanVO> finalPlans = planService.listFinalPlans(userEmail);
        Map<Integer, List<ProductVO>> finalPlanProducts = new HashMap<>();
        for (PlanVO p : finalPlans){
            finalPlanProducts.put(p.getPlanNo(), planService.listProductsOfPlan(p.getPlanNo()));
        }

        @SuppressWarnings("unchecked")
        List<ProductVO> compareList = (List<ProductVO>) session.getAttribute("productCompareList");
        int compareCnt = (compareList == null) ? 0 : compareList.size();

        model.addAttribute("userEmail", userEmail);
        model.addAttribute("boxDetails", boxDetails);
        model.addAttribute("finalPlans", finalPlans);
        model.addAttribute("finalPlanProducts", finalPlanProducts);
        model.addAttribute("hasConfirmed", !finalPlans.isEmpty());
        model.addAttribute("productCompareCount", compareCnt);
        model.addAttribute("progressPercent", 0);

        return "WeddingPlanner/WeddingPlanList";
    }
    
    // 임시 플랜 보관함: 삭제
    @GetMapping("/planner/planbox/remove")
    public String planBoxRemove(@RequestParam("idx") int idx, HttpSession session) {
        // 1) 화면에서 쓰는 소스: session.planBoxDetails에서 동일 인덱스 제거
        @SuppressWarnings("unchecked")
        List<List<ProductVO>> boxDetails = (List<List<ProductVO>>) session.getAttribute("planBoxDetails");
        if (boxDetails != null && idx >= 0 && idx < boxDetails.size()) {
            boxDetails.remove(idx);
            session.setAttribute("planBoxDetails", boxDetails);
        }

        // 2) (병행 유지 시) Draft 박스도 동일 인덱스 제거해서 상태 동기화
        //    DraftPlanServiceImpl.removeFromBox(...)는 session "planBox"를 정리함
        draft.removeFromBox(session, idx);

        // 3) 보관함으로 복귀
        return "redirect:/planner/WeddingPlanList";
    }


    /* ====================== DIY 목록/필터 ====================== */
    @GetMapping("/planner/WeddingDIY")
    public String diy(
        @RequestParam(value="cat",  required=false, defaultValue="1") int cat,
        @RequestParam(value="all",  required=false, defaultValue="false") boolean all,
        @RequestParam(value="q",    required=false) String q,
        @RequestParam(value="ex",   required=false) String ex,
        @RequestParam(value="sort", required=false) String sort,
        @RequestParam(value="cmp",  required=false, defaultValue="false") boolean cmp,
        Model model, HttpSession session
    ){
        String userEmail = ensureUser(session);
        String exPattern = toRegexOr(ex);

        List<ProductVO> products = all
            ? productService.listAll(q, exPattern, sort)
            : productService.listByCategory(cat, q, exPattern, sort);

        PlanVO plan = draft.getOrCreate(userEmail);
        List<ProductVO> selected = draft.getSelectedProducts(userEmail);
        int total = draft.getTotalCost(userEmail);
        boolean isBudgetOk = total <= 2_000_000;

        @SuppressWarnings("unchecked")
        List<ProductVO> compareList = (List<ProductVO>) session.getAttribute("productCompareList");
        if (compareList == null) compareList = new ArrayList<>();
        int compareCnt = compareList.size();

        model.addAttribute("userEmail", userEmail);
        model.addAttribute("products", products);

        model.addAttribute("cat", cat);
        model.addAttribute("all", all);
        model.addAttribute("q", q);
        model.addAttribute("ex", ex);
        model.addAttribute("sort", sort);

        model.addAttribute("selectedPlan", plan);
        model.addAttribute("selectedItems", selected);
        model.addAttribute("selectedTotal", total);
        model.addAttribute("isBudgetOk", isBudgetOk);

        model.addAttribute("showCompare", cmp);
        model.addAttribute("compareList", compareList);
        model.addAttribute("productCompareCount", compareCnt);
        model.addAttribute("progressPercent", 0);

        return "WeddingPlanner/WeddingDIY";
    }

    /* ====================== DIY 액션 ====================== */
    @GetMapping("/planner/plan/select")
    public String planSelect(
        @RequestParam("userEmail") String userEmail,
        @RequestParam("productNo") int productNo,
        @RequestParam(value="cat",  required=false, defaultValue="1") int cat,
        @RequestParam(value="q",    required=false) String q,
        @RequestParam(value="ex",   required=false) String ex,
        @RequestParam(value="sort", required=false) String sort,
        @RequestParam(value="all",  required=false, defaultValue="false") boolean all,
        HttpSession session
    ){
        ensureUser(session);
        ProductVO p = productService.getOne(productNo);
        if (p != null) {
            draft.select(userEmail, p.getProductNo(), p.getCategory());
            cat = p.getCategory();
        }
        return redirectDIY(cat, q, ex, sort, all);
    }

    @GetMapping("/planner/plan/item/delete")
    public String planItemDelete(
        @RequestParam("userEmail") String userEmail,
        @RequestParam("planNo") int planNo,
        @RequestParam("category") int category,
        @RequestParam(value="cat",  required=false, defaultValue="1") int cat,
        @RequestParam(value="q",    required=false) String q,
        @RequestParam(value="ex",   required=false) String ex,
        @RequestParam(value="sort", required=false) String sort,
        @RequestParam(value="all",  required=false, defaultValue="false") boolean all
    ){
        draft.deleteOne(userEmail, category);
        return redirectDIY(cat, q, ex, sort, all);
    }

    @GetMapping("/planner/plan/reset")
    public String planReset(
        @RequestParam("userEmail") String userEmail,
        @RequestParam(value="cat", required=false, defaultValue="1") int cat,
        @RequestParam(value="all", required=false, defaultValue="false") boolean all,
        @RequestParam(value="q",   required=false) String q,
        @RequestParam(value="ex",  required=false) String ex,
        @RequestParam(value="sort",required=false) String sort,
        HttpSession session
    ){
        draft.reset(userEmail);
        return redirectDIY(cat, q, ex, sort, all);
    }

    /** DIY의 "플랜 저장" → 보관함 스냅샷 → Confirm 미리보기 1회 */
    @GetMapping("/planner/plan/save")
    public String planSave(
        @RequestParam("userEmail") String userEmail,
        @RequestParam(value="cat", required=false, defaultValue="1") int cat,
        @RequestParam(value="all", required=false, defaultValue="false") boolean all,
        @RequestParam(value="q",   required=false) String q,
        @RequestParam(value="ex",  required=false) String ex,
        @RequestParam(value="sort",required=false) String sort,
        HttpSession session
    ){
        ensureUser(session);
        List<ProductVO> selected = draft.getSelectedProducts(userEmail);
        if (selected == null) selected = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<List<ProductVO>> boxDetails = (List<List<ProductVO>>) session.getAttribute("planBoxDetails");
        if (boxDetails == null) boxDetails = new ArrayList<>();
        boxDetails.add(new ArrayList<>(selected));
        session.setAttribute("planBoxDetails", boxDetails);

        int idx = boxDetails.size() - 1;
        return "redirect:/planner/WeddingConfirm?idx=" + idx;
    }

    /* ====================== Confirm (세션 보관 확인) ====================== */
    @GetMapping("/planner/WeddingConfirm")
    public String confirm(@RequestParam(value="idx", required=false) Integer idx,
                          Model model, HttpSession session){
        @SuppressWarnings("unchecked")
        List<List<ProductVO>> boxDetails = (List<List<ProductVO>>) session.getAttribute("planBoxDetails");
        if (boxDetails == null) boxDetails = new ArrayList<>();

        int selIdx = -1;
        List<ProductVO> items = new ArrayList<>();
        int total = 0;
        boolean has = false;

        if (!boxDetails.isEmpty()){
            selIdx = (idx != null && idx >= 0 && idx < boxDetails.size()) ? idx : (boxDetails.size() - 1);
            List<ProductVO> raw = boxDetails.get(selIdx);
            if (raw != null) {
                items = new ArrayList<>(raw);
                for (ProductVO p : items) if (p != null) total += p.getCost();
                has = !items.isEmpty();
            }
        }

        @SuppressWarnings("unchecked")
        List<ProductVO> compareList = (List<ProductVO>) session.getAttribute("productCompareList");
        int compareCnt = (compareList == null) ? 0 : compareList.size();

        model.addAttribute("planIndex", selIdx);
        model.addAttribute("planItems", items);
        model.addAttribute("planTotal", total);
        model.addAttribute("hasPlan", has);
        model.addAttribute("progressPercent", 0);
        model.addAttribute("productCompareCount", compareCnt);

        return "WeddingPlanner/WeddingConfirm";
    }

    /** Confirm의 "결정" = 임시 보관 확정(세션만) */
    @GetMapping("/planner/confirm/finalize")
    public String finalizeConfirm(@RequestParam("idx") int idx, HttpSession session){
        ensureUser(session);
        return "redirect:/planner/WeddingPlanList";
    }

    /* ====================== 임시 보관함 → 최종결정(DB INSERT) ====================== */
    @GetMapping("/planner/planbox/finalize")
    public String planBoxFinalize(@RequestParam("idx") int idx, HttpSession session){
        String userEmail = ensureUser(session);

        @SuppressWarnings("unchecked")
        List<List<ProductVO>> boxDetails = (List<List<ProductVO>>) session.getAttribute("planBoxDetails");
        if (boxDetails == null || idx < 0 || idx >= boxDetails.size()) {
            return "redirect:/planner/WeddingPlanList";
        }

        List<ProductVO> items = boxDetails.get(idx);
        int hall=0, studio=0, dress=0, makeup=0;
        if (items != null){
            for (ProductVO p : items){
                if (p == null) continue;
                int cat = p.getCategory();
                if (cat == 0) hall   = p.getProductNo();
                if (cat == 1) studio = p.getProductNo();
                if (cat == 2) dress  = p.getProductNo();
                if (cat == 3) makeup = p.getProductNo();
            }
        }

        PlanVO vo = new PlanVO();
        vo.setUserEmail(userEmail);
        vo.setPackageNo(9999); // DIY 확정
        vo.setHall(hall);
        vo.setStudio(studio);
        vo.setDress(dress);
        vo.setMakeup(makeup);
        planService.finalizeDIY(vo);

        return "redirect:/planner/WeddingPlanList";
    }
    
    @GetMapping("/planner/plan/final/remove")
    public String removeFinalPlan(HttpSession session){
        String userEmail = ensureUser(session);
        planService.deleteFinalByUser(userEmail);
        return "redirect:/planner/WeddingPlanList";
    }


    /* ====================== 상품비교 (세션) ====================== */
    @GetMapping("/planner/compare/add")
    public String compareAdd(@RequestParam("productNo") int productNo,
                             HttpServletRequest req, HttpSession session){
        ensureUser(session);
        @SuppressWarnings("unchecked")
        List<ProductVO> list = (List<ProductVO>) session.getAttribute("productCompareList");
        if (list == null) list = new ArrayList<>();
        boolean exists = false;
        for (ProductVO pv : list){
            if (pv != null && pv.getProductNo() == productNo){ exists = true; break; }
        }
        if (!exists && list.size() < 4){
            ProductVO p = productService.getOne(productNo);
            if (p != null) list.add(p);
        }
        session.setAttribute("productCompareList", list);
        return backTo(req, "/planner/WeddingDIY");
    }

    @GetMapping("/planner/compare/remove")
    public String compareRemove(@RequestParam("productNo") int productNo,
                                HttpServletRequest req, HttpSession session){
        @SuppressWarnings("unchecked")
        List<ProductVO> list = (List<ProductVO>) session.getAttribute("productCompareList");
        if (list == null) list = new ArrayList<>();
        list.removeIf(p -> p != null && p.getProductNo() == productNo);
        session.setAttribute("productCompareList", list);
        return backTo(req, "/planner/WeddingDIY");
    }

    @GetMapping("/planner/compare/clear")
    public String compareClear(HttpServletRequest req, HttpSession session){
        session.setAttribute("productCompareList", new ArrayList<ProductVO>());
        return backTo(req, "/planner/WeddingDIY");
    }

    @GetMapping("/planner/compare/compare")
    public String compareGoToDIY(){
        return "redirect:/planner/WeddingDIY?cmp=true#cmp-result";
    }
    
    // 플랜 비교 (임시보관함 인덱스 기반)
    @GetMapping("/planner/WeddingPlanCompare")
    public String planBoxCompare(
            @RequestParam(value = "idx", required = false) List<Integer> idx,
            Model model, HttpSession session) {

        // 세션 사용자 확인 (샘플 user1@example.com 세팅)
        String userEmail = ensureUser(session);

        @SuppressWarnings("unchecked")
        List<List<ProductVO>> boxDetails =
                (List<List<ProductVO>>) session.getAttribute("planBoxDetails");
        if (boxDetails == null) boxDetails = new ArrayList<>();

        // 선택이 없으면 기본으로 앞에서 2개(있으면) 선택
        List<Integer> cmpIdx = new ArrayList<>();
        if (idx != null) {
            for (Integer i : idx) {
                if (i != null && i >= 0 && i < boxDetails.size()) {
                    cmpIdx.add(i);
                }
            }
        }
        if (cmpIdx.isEmpty()) {
            for (int i = 0; i < Math.min(2, boxDetails.size()); i++) {
                cmpIdx.add(i);
            }
        }

        // 최대 4개 제한
        if (cmpIdx.size() > 4) {
            cmpIdx = cmpIdx.subList(0, 4);
        }

        // 비교 대상 플랜들과 합계 계산
        List<List<ProductVO>> plans = new ArrayList<>();
        List<Integer> totals = new ArrayList<>();
        for (Integer i : cmpIdx) {
            List<ProductVO> one = (i >= 0 && i < boxDetails.size())
                    ? boxDetails.get(i) : new ArrayList<>();
            plans.add(one);

            int sum = 0;
            if (one != null) {
                for (ProductVO p : one) {
                    if (p != null) {
                        // ProductVO.cost 사용 (세션에서 쓰는 VO 그대로 사용)
                        sum += p.getCost();
                    }
                }
            }
            totals.add(sum);
        }

        model.addAttribute("plans", plans);
        model.addAttribute("cmpIdx", cmpIdx);
        model.addAttribute("totals", totals);
        model.addAttribute("progressPercent", 0);

        return "WeddingPlanner/WeddingPlanCompare";
    }


    /* ====================== 테마별 패키지 ====================== */

    /** 패키지 목록 + 필터 */
    @GetMapping("/planner/WeddingCategory")
    public String category(
            @RequestParam(value="t",    required=false) Integer type,     // 0~4
            @RequestParam(value="q",    required=false) String q,         // 키워드(상품명)
            @RequestParam(value="city", required=false) String city,      // 지역(주소 포함검색)
            @RequestParam(value="min",  required=false) Integer min,      // 예산(만원)
            @RequestParam(value="max",  required=false) Integer max,      // 예산(만원)
            @RequestParam(value="sort", required=false) String sort,      // priceAsc, priceDesc, pop(default)
            Model model, HttpSession session
    ){
        String userEmail = ensureUser(session);
        List<PackageCardVO> list = packageService.searchPackages(type, q, city, min, max, sort);
        int count = (list == null) ? 0 : list.size();

        @SuppressWarnings("unchecked")
        List<ProductVO> compareList = (List<ProductVO>) session.getAttribute("productCompareList");
        int compareCnt = (compareList == null) ? 0 : compareList.size();

        model.addAttribute("packages", list);
        model.addAttribute("packageCount", count);

        // 필터값 다시 뷰로
        model.addAttribute("t", type);
        model.addAttribute("q", q);
        model.addAttribute("city", city);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("sort", sort);

        model.addAttribute("userEmail", userEmail);
        model.addAttribute("progressPercent", 0);
        model.addAttribute("productCompareCount", compareCnt);

        return "WeddingPlanner/WeddingCategory";
    }

    /** 패키지 선택 → (세션 보관함에 스냅샷) → 보관함으로 이동 */
    @GetMapping("/planner/package/select")
    public String selectPackage(@RequestParam("packageNo") int packageNo, HttpSession session){
        ensureUser(session);
        PackageCardVO p = packageService.getOne(packageNo);
        if (p != null){
            List<ProductVO> snapshot = new ArrayList<>();
            // 구성 4종을 ProductVO로 변환(세션 보관과 동일 포맷)
            ProductVO hall = productService.getOne(p.getHallNo());
            ProductVO stud = productService.getOne(p.getStudioNo());
            ProductVO dres = productService.getOne(p.getDressNo());
            ProductVO make = productService.getOne(p.getMakeupNo());
            if (hall != null) snapshot.add(hall);
            if (stud != null) snapshot.add(stud);
            if (dres != null) snapshot.add(dres);
            if (make != null) snapshot.add(make);

            @SuppressWarnings("unchecked")
            List<List<ProductVO>> boxDetails = (List<List<ProductVO>>) session.getAttribute("planBoxDetails");
            if (boxDetails == null) boxDetails = new ArrayList<>();
            boxDetails.add(snapshot);
            session.setAttribute("planBoxDetails", boxDetails);
        }
        return "redirect:/planner/WeddingPlanList";
    }

    /* -------------------------- 리다이렉트 헬퍼 -------------------------- */
    private String redirectDIY(int cat, String q, String ex, String sort, boolean all){
        StringBuilder sb = new StringBuilder("redirect:/planner/WeddingDIY?");
        if (all) sb.append("all=true&");
        else     sb.append("cat=").append(cat).append('&');
        if (q   != null && !q.isBlank())   sb.append("q=").append(enc(q)).append('&');
        if (ex  != null && !ex.isBlank())  sb.append("ex=").append(enc(ex)).append('&');
        if (sort!= null && !sort.isBlank())sb.append("sort=").append(enc(sort)).append('&');
        if (sb.charAt(sb.length()-1)=='&') sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
