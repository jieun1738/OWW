package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.LoanVO;

public interface LoanService {
    List<LoanVO> findAll(String status, int offset, int limit);
    int count(String status);
    LoanVO findById(long id);
    void approve(long id, String admin, String memo);
    void reject(long id, String admin, String memo);
}
