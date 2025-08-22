// com.sboot.moabayo.service.CardProductService
package com.oww.oww1.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oww.oww1.mapper.BudgetMapper;
import com.oww.oww1.VO.BudgetForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetMapper budgetMapper;

    @Transactional
    public BudgetForm getBudget(String email) {
    	String key = normalizeEmail(email);
    	return (BudgetForm) budgetMapper.findByUserEmail(key);
    }
    
    
	/* 아래는 공용 메서드입니다. 참고해주세요 */
    private String normalizeEmail(String email) {
        return (email == null || email.isBlank())
                ? "test@test.com"   // 컨트롤러/다른 서비스와 동일한 폴백 이메일로 통일
                : email.trim();
    }


	public Object totalForSidebar(String email) {
		// TODO Auto-generated method stub
		return null;
	}


	public void save(String email, @Valid BudgetForm budget) {
		// TODO Auto-generated method stub
		
	}
}