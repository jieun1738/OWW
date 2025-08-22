// src/main/java/com/example/demo/mapper/DashboardMapper.java
package com.example.demo.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.ContractAggVO;
import com.example.demo.vo.MonthlyPlansVO;
import com.example.demo.vo.MonthlySalesVO;
import com.example.demo.vo.PlanSummaryVO;
import com.example.demo.vo.ProductRankVO;

@Mapper
public interface DashboardMapper {

    // KPI
    long salesBetween(@Param("start") Date start, @Param("end") Date end);
    int  newUsersBetween(@Param("start") Date start, @Param("end") Date end);
    int  activeProducts();
    int  openPlans();

    // 월별 시계열
    List<MonthlySalesVO> monthlySales(@Param("start") Date start, @Param("end") Date end);
    List<MonthlyPlansVO> monthlyPlans(@Param("start") Date start, @Param("end") Date end);

    // 계약 집계
    ContractAggVO contractAgg();

    // 위젯
    List<PlanSummaryVO>  recentPlans(@Param("limit") int limit);
    List<ProductRankVO>  topProducts(@Param("limit") int limit);
}
