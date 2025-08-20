package com.wedding.oww.service;

import com.wedding.oww.mapper.PackageMapper;
import com.wedding.oww.mapper.PlanMapper;
import com.wedding.oww.mapper.ProductMapper;
import com.wedding.oww.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

    private final ProductMapper productMapper;
    private final PackageMapper packageMapper;
    private final PlanMapper planMapper;

    /* ========== 기본 조회들 ========== */

    @Override
    public List<ProductVO> getProducts(Integer category, String q, String sort) {
        return productMapper.search(category, q, sort);
    }

    @Override
    public List<ProductVO> getProductsByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return List.of();
        return productMapper.findByIds(ids);
    }

    @Override
    public List<PackageVO> getPackages(Integer type) {
        return packageMapper.findAllByType(type);
    }

    @Override
    public PackageVO getPackage(Long packageNo) {
        return packageMapper.findById(packageNo);
    }

    @Override
    public void confirmDIY(String email, Long hall, Long studio, Long dress, Long makeup) {
        planMapper.insertDIY(email, hall, studio, dress, makeup);
    }

    @Override
    public void confirmPackage(String email, Long packageNo) {
        planMapper.insertPackage(email, packageNo);
    }

    @Override
    public List<PlanVO> getCommittedPlans(String email) {
        return planMapper.findCommittedByUser(email);
    }

    @Override
    public int getCommittedPlanCount(String email) {
        Integer c = planMapper.countCommittedByUser(email);
        return c == null ? 0 : c;
    }

    /* ========== 보강: 합계/프리뷰 계산 ========== */

    @Override
    public List<PackagePreviewDTO> getPackagePreviews(Integer type) {
        List<PackageVO> list = getPackages(type);
        if (list == null) return List.of();

        // 패키지별 구성 제품의 금액 합산 -> 할인 적용
        return list.stream().map(pkg -> {
            List<Long> ids = new ArrayList<>();
            if (pkg.getHall() != null) ids.add(pkg.getHall());
            if (pkg.getStudio() != null) ids.add(pkg.getStudio());
            if (pkg.getDress() != null) ids.add(pkg.getDress());
            if (pkg.getMakeup() != null) ids.add(pkg.getMakeup());
            int sum = getProductsByIds(ids).stream()
                    .map(ProductVO::getCost).filter(Objects::nonNull)
                    .mapToInt(Integer::intValue).sum();
            int discount = pkg.getDiscount() == null ? 0 : pkg.getDiscount();
            int discounted = sum - (sum * discount / 100);
            return PackagePreviewDTO.builder()
                    .packageNo(pkg.getPackageNo())
                    .type(pkg.getType())
                    .discount(discount)
                    .total(sum)
                    .discounted(discounted)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<PlanCardDTO> getPlanCards(String email) {
        List<PlanVO> plans = getCommittedPlans(email);
        if (plans == null) return List.of();

        return plans.stream().map(pl -> {
            List<Long> ids = new ArrayList<>();
            if (pl.getHall() != null) ids.add(pl.getHall());
            if (pl.getStudio() != null) ids.add(pl.getStudio());
            if (pl.getDress() != null) ids.add(pl.getDress());
            if (pl.getMakeup() != null) ids.add(pl.getMakeup());

            List<ProductVO> prods = getProductsByIds(ids);
            int sum = prods.stream().map(ProductVO::getCost).filter(Objects::nonNull)
                    .mapToInt(Integer::intValue).sum();

            // 대표 썸네일: 홀 이미지 우선, 없으면 첫 제품
            String thumb = null;
            if (pl.getHall() != null) {
                ProductVO hall = prods.stream()
                        .filter(p -> p.getProductNo().equals(pl.getHall()))
                        .findFirst().orElse(null);
                if (hall != null) thumb = hall.getImg();
            }
            if (thumb == null && !prods.isEmpty()) {
                thumb = prods.get(0).getImg();
            }

            return PlanCardDTO.builder()
                    .planNo(pl.getPlanNo())
                    .packageBased(pl.getPackageNo() != null)
                    .total(sum)
                    .thumbnailImg(thumb)
                    .build();
        }).collect(Collectors.toList());
    }
}
