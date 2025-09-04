package com.oww.oww1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PlannerPackageAdminVVO;
import com.oww.oww1.mapper.Plannermapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlannerserviceImpl implements Plannerservice {

    private final Plannermapper mapper;

    @Override
    public List<PlannerPackageAdminVVO> get_category_cards() {
        return mapper.select_package_admin_list();
    }

    @Override
    public PlannerPackageAdminVVO get_category_card(int package_no) {
        return mapper.select_package_admin_one(package_no);
    }
}
