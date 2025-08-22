package com.oww.oww1.service;

import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanCardDTO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.PackageMapper;
import com.oww.oww1.mapper.PlanMapper;
import com.oww.oww1.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * PlannerService 구현 (원시형 int 기반, 단순 로직)
 * - VO는 모두 스네이크 필드명을 사용함(product_no 등)
 * - DIY 구분은 package_no == 9999 로 판단(SELECT에서 NVL로 보정됨)
 * - 마이페이지 관련 기능은 호출하지 않음
 */
@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

    private static final int DIY_SENTINEL = 9999;

    private final ProductMapper productMapper;
    private final PackageMapper packageMapper;
    private final PlanMapper planMapper;

    // 제품 검색
    @Override
    public List<ProductVO> getProducts(Integer category, String q, String sort) {
        return productMapper.search(category, q, sort);
    }

    // 제품 다건 조회
    @Override
    public List<ProductVO> getProductsByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return List.of();
        return productMapper.findByIds(ids);
    }

    // 패키지 목록/단건
    @Override
    public List<PackageVO> getPackages(Integer type) {
        return packageMapper.findByType(type);
    }

    @Override
    public PackageVO getPackage(int packageNo) {
        return packageMapper.findById(packageNo);
    }

    // DIY 확정
    @Override
    public void confirmDIY(String email, int hall, int studio, int dress, int makeup) {
        planMapper.insertDIY(email, hall, studio, dress, makeup);
    }

    // 패키지 확정
    @Override
    public void confirmPackage(String email, int packageNo) {
        planMapper.insertPackage(email, packageNo);
    }

    // 확정 건수
    @Override
    public int getCommittedPlanCount(String email) {
        Integer cnt = planMapper.countCommittedByUser(email);
        return cnt == null ? 0 : cnt;
    }

    // 카드형 목록(썸네일/합계)
    @Override
    public List<PlanCardDTO> getPlanCards(String email) {
        List<PlanVO> plans = planMapper.findCommittedByUser(email);
        if (plans == null || plans.isEmpty()) return List.of();

        List<PlanCardDTO> cards = new ArrayList<>();

        for (PlanVO pl : plans) {
            List<Integer> ids = new ArrayList<>();
            if (pl.getHall()   > 0) ids.add(pl.getHall());
            if (pl.getStudio() > 0) ids.add(pl.getStudio());
            if (pl.getDress()  > 0) ids.add(pl.getDress());
            if (pl.getMakeup() > 0) ids.add(pl.getMakeup());

            List<ProductVO> prods = getProductsByIds(ids);

            int sum = 0;
            String thumb = null;

            for (ProductVO p : prods) {
                sum += p.getCost();
                // 홀(카테고리=0) 이미지를 우선 썸네일로 사용, 없으면 첫 이미지
                if (thumb == null && p.getCategory() == 0 && p.getImg() != null && !p.getImg().isEmpty()) {
                    thumb = p.getImg();
                }
            }
            if (thumb == null && !prods.isEmpty()) {
                String img0 = prods.get(0).getImg();
                if (img0 != null && !img0.isEmpty()) thumb = img0;
            }

            boolean packageBased = (pl.getPackage_no() != DIY_SENTINEL);

            cards.add(PlanCardDTO.builder()
                    .planNo((long) pl.getPlan_no())
                    .packageBased(packageBased)
                    .total(sum)
                    .thumbnailImg(thumb)
                    .build());
        }
        return cards;
    }
    
    @Override
    public List<PackageVO> getPackagePreviews(Integer type) {
        // 불필요한 가공 없이 그대로 반환
        return packageMapper.findByType(type);
    }
}
