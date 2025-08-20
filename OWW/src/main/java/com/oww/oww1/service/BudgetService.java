package com.oww.oww1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oww.oww1.VO.BudgetVO;
import com.oww.oww1.VO.PlanProgressVO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.MypageMapper;


@Service
public class BudgetService {
	@Autowired
	private MypageMapper mypageMapper;
	
	BudgetVO userBudget;
	   public BudgetVO getBudget(String email) {
	        this.userBudget = mypageMapper.getBudget(email);
	        return userBudget;
	   }
	   
	   public int sumBudget(String email) {
		   int sumBudget = mypageMapper.sumBudget(email);		   
		   return sumBudget;
	   }
	   
	   public List <ProductVO> getProductInfo(String email) {
	        return mypageMapper.getProductInfo(email);
	       
	   }
	   public PlanVO getPlan(String email) {
		   return mypageMapper.getPlan(email);
	   }
	   
	   public int getContractProgess(int plan_no) {
		 return mypageMapper.getContractProgress(plan_no);  
	   }
	   
	   public int getPaidProgess(int plan_no) {
			 return mypageMapper.getPaidProgress(plan_no);  
		   }

	   public Boolean setProgress(PlanProgressVO planprogvo) {
		   return mypageMapper.setProgress(planprogvo);		
	   }
	   
	   public PlanProgressVO getPlanProgress(int plan_no){
		   return mypageMapper.getPlanProgress(plan_no);
	   }

	   public int getDiscount(int package_no) {
		return mypageMapper.getDiscount(package_no);
	   }
}
