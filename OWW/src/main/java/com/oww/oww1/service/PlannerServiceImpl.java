package com.oww.oww1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PackagePreviewDTO;
import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanCardDTO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.PackageMapper;
import com.oww.oww1.mapper.PlanMapper;
import com.oww.oww1.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

    private final ProductMapper productMapper;
    private final PackageMapper packageMapper;
    private final PlanMapper planMapper;

    public List<ProductVO> getProducts(int category, String q, String sort) {
        Integer cat = (category < 0 ? null : Integer.valueOf(category));
        String qq = (q == null || q.trim().isEmpty()) ? null : q;
        String ss = (sort == null || sort.trim().isEmpty()) ? null : sort;
        return productMapper.search(cat, qq, ss);
    }

    public List<ProductVO> getProductsByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<ProductVO>();
        return productMapper.findByIds(ids);
    }

    public List<PackageVO> getPackages(int type) {
        Integer t = (type < 0 ? null : Integer.valueOf(type));
        return packageMapper.findAllByType(t);
    }

    public PackageVO getPackage(int packageNo) {
        return packageMapper.findById(packageNo);
    }

    public void confirmDIY(String email, int hall, int studio, int dress, int makeup) {
        if (email == null || email.trim().isEmpty()) return;
        planMapper.insertDIY(email, hall, studio, dress, makeup);
    }

    public void confirmPackage(String email, int packageNo) {
        if (email == null || email.trim().isEmpty()) return;
        planMapper.insertPackage(email, packageNo);
    }

    public List<PlanVO> getCommittedPlans(String email) {
        if (email == null || email.trim().isEmpty()) return new ArrayList<PlanVO>();
        return planMapper.findCommittedByUser(email);
    }

    public int getCommittedPlanCount(String email) {
        Integer cnt = planMapper.countCommittedByUser(email);
        return (cnt == null ? 0 : cnt.intValue());
    }

    public List<PackagePreviewDTO> getPackagePreviews(int limit) {
        List<PackageVO> list = packageMapper.findAllByType(null);
        List<PackagePreviewDTO> out = new ArrayList<PackagePreviewDTO>();
        int n = 0;
        for (PackageVO p : list) {
            PackagePreviewDTO dto = new PackagePreviewDTO();
            dto.setPackageNo(p.getPackageNo());
            dto.setType(p.getType());
            dto.setDiscount(p.getDiscount());
            out.add(dto);
            n++;
            if (limit > 0 && n >= limit) break;
        }
        return out;
    }

    public List<PlanCardDTO> getPlanCards(String email) {
        List<PlanVO> plans = getCommittedPlans(email);
        List<PlanCardDTO> out = new ArrayList<PlanCardDTO>();
        for (PlanVO v : plans) {
            PlanCardDTO c = new PlanCardDTO();
            c.setPlanNo(v.getPlanNo());
            c.setUserEmail(v.getUserEmail());
            c.setPackageNo(v.getPackageNo());
            c.setHall(v.getHall());
            c.setStudio(v.getStudio());
            c.setDress(v.getDress());
            c.setMakeup(v.getMakeup());
            out.add(c);
        }
        return out;
    }

    public PlanVO findRecentPlan(String email) {
        List<PlanVO> list = getCommittedPlans(email);
        return list.isEmpty() ? null : list.get(0);
    }
}
