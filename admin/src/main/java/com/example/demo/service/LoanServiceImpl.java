package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.LoanMapper;
import com.example.demo.vo.LoanVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanMapper loanMapper;

    @Override
    public List<LoanVO> findAll(String status, int offset, int limit) {
        return loanMapper.findAll(status, offset, limit);
    }

    @Override
    public int count(String status) {
        return loanMapper.count(status);
    }

    @Override
    public LoanVO findById(long id) {
        return loanMapper.findById(id);
    }

    @Override
    @Transactional
    public void approve(long id, String admin, String memo) {
        loanMapper.approve(id, admin, memo);
    }

    @Override
    @Transactional
    public void reject(long id, String admin, String memo) {
        loanMapper.reject(id, admin, memo);
    }

	@Override
	public void insert(LoanVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(LoanVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
