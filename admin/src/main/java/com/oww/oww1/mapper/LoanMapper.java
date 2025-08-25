package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.oww.oww1.vo.LoanVO;

@Mapper
public interface LoanMapper {
    List<LoanVO> findAll();
    LoanVO findById(Long id);
    void approve(Long id);
    void reject(Long id);
}
