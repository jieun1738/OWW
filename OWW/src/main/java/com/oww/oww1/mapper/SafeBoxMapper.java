package com.oww.oww1.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.PeriodAmt;

@Mapper
public interface SafeBoxMapper {

    // --- GOAL (목표/저축액) ---
    Optional<Long> findTargetAmount(@Param("email") String email);

    int upsertTargetAmount(@Param("email") String email,
                           @Param("target") long target);

    long sumSaved(@Param("email") String email);

    // --- SAVING LOG (저축 기록) ---
    int insertSaving(@Param("email") String email,
                     @Param("amount") long amount,
                     @Param("memo") String memo);

    // --- 통계 ---
    List<PeriodAmt> selectDailyThisMonth(@Param("email") String email);

    List<PeriodAmt> selectMonthly12(@Param("email") String email);
}
