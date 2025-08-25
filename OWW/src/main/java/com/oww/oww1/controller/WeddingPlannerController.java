package com.oww.oww1.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oww.oww1.VO.DraftPlanVO;
import com.oww.oww1.VO.PackageAdminViewVO;
import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.VO.UserVO;
import com.oww.oww1.service.DraftPlanService;
import com.oww.oww1.service.PackageService;
import com.oww.oww1.service.PlanService;
import com.oww.oww1.service.ProductService;
import com.oww.oww1.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * Wedding Planner Controller (링크 방식만 사용: @RequestMapping)
 * HTML/CSS/레이아웃은 수정하지 않음. 템플릿이 기대하는 모델 키에 맞춰 바인딩만 합니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping
public class WeddingPlannerController {

    private final ProductService productService;
    private final PackageService packageService;
    private final PlanService planService;
    private final DraftPlanService draftPlanService;
    private final UserService userService;

    /** 템플릿이 ${user.userName}, ${user.userEmail}을 사용하므로 세션에 UserVO 보장 */
    private UserVO ensureUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object v = session.getAttribute("user");
        if (v instanceof UserVO) return (UserVO) v;

        // 통합 전 임시 이메일(샘플 데이터 존재 가정)
        String email = "user1@example.com";
        UserVO user = userService.findByEmail(email);
        if (user == null) {
            user = new UserVO();
            user.setUserEmail(email);
            user.setUserName("게스트");
            user.setSocialType(0);
        }
        session.setAttribute("user", user);
        return user;
    }

    // ===== 플래너 메인 요약 =====
    @RequestMapping({"/planner/info", "/info"})
    public String plannerInfo(HttpServletRequest req, Model model) {
        UserVO user = ensureUser(req);

        // 추천 패키지(간단: 상위 6개)
        List<PackageAdminViewVO> all = packageService.findAllPackages();
        List<PackageAdminViewVO> rec = all.subList(0, Math.min(6, all.size()));

        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 0);
        model.addAttribute("recommendedPackages", rec); // 템플릿 키에 맞춤
        return "WeddingPlanner/WeddingPlannerInfo";
    }

    // ===== 소개 =====
    @RequestMapping({"/planner/intro", "/intro"})
    public String plannerIntro(HttpServletRequest req, Model model) {
        model.addAttribute("user", ensureUser(req));
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlannerIntro";
    }

    // ===== 테마별 패키지 =====
    @RequestMapping({"/planner/category", "/category"})
    public String plannerCategory(
            HttpServletRequest req, Model model,
            @RequestParam(value="type", required=false, defaultValue="-1") int type) {

        UserVO user = ensureUser(req);
        List<PackageAdminViewVO> list = (type < 0)
                ? packageService.findAllPackages()
                : packageService.findByType(type);

        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 0);
        model.addAttribute("type", type);           // 템플릿: ${type}
        model.addAttribute("packageList", list);    // 템플릿: ${packageList}
        return "WeddingPlanner/WeddingCategory";
    }

    // ===== DIY =====
    @RequestMapping({"/planner/diy", "/diy"})
    public String plannerDIY(
            HttpServletRequest req, Model model,
            @RequestParam(value="category", required=false, defaultValue="-1") int category, // 템플릿 파라미터명 = category
            @RequestParam(value="keyword",  required=false, defaultValue="")  String keyword) {

        UserVO user = ensureUser(req);

        // 상품 목록 로드 후 간단 필터(스키마 변경 없이 컨트롤러에서 처리)
        List<ProductVO> products = productService.findAll();
        if (category >= 0) {
            List<ProductVO> filt = new ArrayList<>();
            for (ProductVO p : products) if (p.getCategory() == category) filt.add(p);
            products = filt;
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            List<ProductVO> filt = new ArrayList<>();
            for (ProductVO p : products) {
                String nm = p.getProductName() == null ? "" : p.getProductName().toLowerCase();
                if (nm.contains(kw)) filt.add(p);
            }
            products = filt;
        }

        // 템플릿이 기대하는 키로 바인딩
        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 0);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("products", products); // 템플릿: ${products}

        model.addAttribute("draft", draftPlanService.getCurrent(req.getSession()));      // 현재 작업중
        model.addAttribute("draftPlans", draftPlanService.getDrafts(req.getSession()));  // 임시 보관함

        // 비교 UI(하단 테이블) - 기본은 비어있게
        model.addAttribute("compareProducts", Collections.emptyList());
        model.addAttribute("compareItems", Collections.emptyList());
        model.addAttribute("showCompare", 0);

        return "WeddingPlanner/WeddingDIY";
    }

    // DIY: 선택 적용 (템플릿 form의 파라미터명 그대로)
    @RequestMapping({"/planner/diy/select", "/diy/select"})
    public String diySelect(HttpServletRequest req,
                            @RequestParam("category") int category,
                            @RequestParam("productNo") int productNo) {
        draftPlanService.addOrUpdateCurrent(req.getSession(), category, productNo);
        return "redirect:/planner/diy";
    }

    // DIY: 현재 작업중 플랜 임시보관함 저장
    @RequestMapping({"/planner/diy/save", "/diy/save"})
    public String diySave(HttpServletRequest req) {
        String name = "임시플랜-" + new java.text.SimpleDateFormat("HHmmss").format(new Date());
        draftPlanService.saveCurrentAsDraft(req.getSession(), name);
        return "redirect:/planner/diy";
    }

    // ===== 임시 플랜 보관함(플랜 리스트/비교/확정 목록) =====
    // 템플릿이 링크하는 경로가 /planner/plan/list 이므로 이 경로를 포함해 전부 매핑
    @RequestMapping({"/planner/plan/list", "/planner/planlist", "/planner/list", "/planlist", "/list"})
    public String planList(HttpServletRequest req, Model model,
                           @RequestParam(value="showCompare", required=false, defaultValue="0") int showCompare) {
        UserVO user = ensureUser(req);

        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 0);

        // 템플릿 키에 정확히 맞춤
        model.addAttribute("draftPlans",     draftPlanService.getDrafts(req.getSession()));
        model.addAttribute("confirmedPlans", planService.findPlansByUser(user.getUserEmail()));
        model.addAttribute("showCompare", showCompare);
        return "WeddingPlanner/WeddingPlanList";
    }

    // 임시 보관함: 항목 제거
    @RequestMapping({"/planner/drafts/remove", "/drafts/remove"})
    public String draftsRemove(HttpServletRequest req, @RequestParam("index") int index) {
        draftPlanService.removeByIndex(req.getSession(), index);
        return "redirect:/planner/plan/list";
    }

    // ===== 패키지 확정 =====
    // 링크형 GET: /planner/confirm?packageNo=NN
    @RequestMapping({"/planner/confirm"})
    public String confirmRouter(HttpServletRequest req, Model model,
                                @RequestParam(value="packageNo", required=false) Integer packageNo) {
        if (packageNo == null) {
            // 단순 진입도 에러 없이 화면 열리도록
            model.addAttribute("user", ensureUser(req));
            model.addAttribute("progressPercent", 100);
            return "WeddingPlanner/WeddingConfirm";
        }
        return confirmPackage(req, model, packageNo.intValue());
    }

    // form POST: /planner/package/confirm
    @RequestMapping({"/planner/package/confirm"})
    public String confirmPackagePost(HttpServletRequest req, Model model,
                                     @RequestParam("packageNo") int packageNo) {
        return confirmPackage(req, model, packageNo);
    }

    // 실제 확정 처리
    private String confirmPackage(HttpServletRequest req, Model model, int packageNo) {
        UserVO user = ensureUser(req);
        PackageVO pkg = packageService.findPackageByNo(packageNo);
        if (pkg == null) return "redirect:/planner/category";

        PlanVO vo = new PlanVO();
        vo.setUserEmail(user.getUserEmail());
        vo.setPackageNo(pkg.getPackageNo());
        vo.setHall(pkg.getHall());
        vo.setStudio(pkg.getStudio());
        vo.setDress(pkg.getDress());
        vo.setMakeup(pkg.getMakeup());
        planService.savePlan(vo);

        model.addAttribute("confirmedPlan", vo);
        model.addAttribute("confirmed", vo);
        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 100);
        return "WeddingPlanner/WeddingConfirm";
    }

    // ===== DIY 확정 =====
    @RequestMapping({"/planner/confirm/diy", "/confirm/diy"})
    public String confirmDIY(HttpServletRequest req, Model model,
                             @RequestParam(value="index", required=false, defaultValue="-1") int index) {
        UserVO user = ensureUser(req);
        DraftPlanVO src = (index < 0)
                ? draftPlanService.getCurrent(req.getSession())
                : draftPlanService.getByIndex(req.getSession(), index);
        if (src == null) return "redirect:/planner/diy";

        PlanVO vo = new PlanVO();
        vo.setUserEmail(user.getUserEmail());
        vo.setPackageNo(9999); // DIY는 9999
        vo.setHall(src.getHall());
        vo.setStudio(src.getStudio());
        vo.setDress(src.getDress());
        vo.setMakeup(src.getMakeup());
        planService.savePlan(vo);

        model.addAttribute("confirmedPlan", vo);
        model.addAttribute("confirmed", vo);
        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 100);
        return "WeddingPlanner/WeddingConfirm";
    }

    // ===== 상품 비교(표 전용) =====
    @RequestMapping({"/planner/productcompare", "/productcompare"})
    public String productCompare(HttpServletRequest req, Model model,
                                 @RequestParam(value="nos", required=false, defaultValue="") String nos) {
        UserVO user = ensureUser(req);
        List<ProductVO> list = new ArrayList<>();
        if (!nos.trim().isEmpty()) {
            for (String p : nos.split(",")) {
                try {
                    int no = Integer.parseInt(p.trim());
                    ProductVO v = productService.findByPk(no);
                    if (v != null) list.add(v);
                } catch (Exception ignore) {}
            }
        }
        model.addAttribute("compareItems", list); // 템플릿 키
        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 0);
        model.addAttribute("showCompare", 1);
        return "WeddingPlanner/WeddingProductCompare";
    }

    // ===== 임시 플랜 비교(표 전용) =====
    @RequestMapping({"/planner/plancompare", "/plancompare"})
    public String planCompare(HttpServletRequest req, Model model,
                              @RequestParam(value="indices", required=false, defaultValue="") String indices) {
        UserVO user = ensureUser(req);
        List<DraftPlanVO> drafts = draftPlanService.getDrafts(req.getSession());
        List<DraftPlanVO> pick = new ArrayList<>();
        if (!indices.trim().isEmpty()) {
            for (String p : indices.split(",")) {
                try {
                    int idx = Integer.parseInt(p.trim());
                    if (idx >= 0 && idx < drafts.size()) pick.add(drafts.get(idx));
                } catch (Exception ignore) {}
            }
        }
        model.addAttribute("compareDrafts", pick); // 템플릿 키
        model.addAttribute("user", user);
        model.addAttribute("progressPercent", 0);
        model.addAttribute("showCompare", 1);
        return "WeddingPlanner/WeddingPlanCompare";
    }
}
