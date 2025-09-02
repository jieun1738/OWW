package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.BudgetVO;
import com.oww.oww1.VO.PaymentDTO;
import com.oww.oww1.VO.PlanProgressVO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;


@Mapper
public interface MypageMapper {
	public BudgetVO getBudget(String user_email);	
	
	public int sumBudget(String user_email);

	public List<ProductVO> getProductInfo(String email);
	
	public PlanVO getPlan(@Param("user_email") String user_email);
	
	public int getContractProgress(int plan_no);
	
	public int getPaidProgress(int plan_no);

	public Boolean setProgress(PlanProgressVO planprogvo);
	
	public PlanProgressVO getPlanProgress(int plan_no);
	
	public int getDiscount(@Param("package_no") int package_no);
	
	public int savePayment(PaymentDTO payment);
	
	public int updateContract(@Param("contract_category") String contract_category,
		    @Param("YorN") String YorN, @Param("plan_no") int  plan_no);

	public int setBudget(BudgetVO newBudget);

	public int updateBudget(BudgetVO newBudget);
	
}
