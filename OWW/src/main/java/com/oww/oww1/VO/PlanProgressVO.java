package com.oww.oww1.VO;

import lombok.Data;

@Data
public class PlanProgressVO {
    int plan_no;

    String contract_hall;
    int pay_hall;
    
    String contract_stud;
    int pay_stud;
    
    String contract_dres;
    int pay_dres;
    
    String contract_make;
    int pay_make;
 
    
    
	Boolean contract_hall_ch, pay_hall_ch;    
    Boolean contract_stud_ch, pay_stud_ch;    
    Boolean contract_dres_ch, pay_dres_ch;    
    Boolean contract_make_ch, pay_make_ch;
  
    
    public int sumAllpay() {
    	int result = pay_hall+pay_stud +pay_dres+pay_make;
		return result;
    	
    }
   
}
