package com.oww.oww1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.oww.oww1.VO.BudgetForm;

@Mapper
public interface BudgetMapper {

	BudgetForm findByUserEmail(String key);
	
	

}
