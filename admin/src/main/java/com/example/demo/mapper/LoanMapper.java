package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.LoanVO;

@Mapper
public interface LoanMapper {
    List<LoanVO> findAll();
    LoanVO findById(Long id);
    void approve(Long id);
    void reject(Long id);
}
