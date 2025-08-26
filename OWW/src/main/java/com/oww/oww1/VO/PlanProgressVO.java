package com.oww.oww1.VO;

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
  
    
    public long sumAllpay() {
    	long result = pay_hall+pay_stud +pay_dres+pay_make;
		return result;
    	
    }
    
    
    public Boolean getContract_hall_ch() {
		return contract_hall_ch;
	}
	public void setContract_hall_ch(Boolean contract_hall_ch) {
		this.contract_hall_ch = contract_hall_ch;
	}
	public Boolean getPay_hall_ch() {
		return pay_hall_ch;
	}
	public void setPay_hall_ch(Boolean pay_hall_ch) {
		this.pay_hall_ch = pay_hall_ch;
	}
	public Boolean getContract_stud_ch() {
		return contract_stud_ch;
	}
	public void setContract_stud_ch(Boolean contract_stud_ch) {
		this.contract_stud_ch = contract_stud_ch;
	}
	public Boolean getPay_stud_ch() {
		return pay_stud_ch;
	}
	public void setPay_stud_ch(Boolean pay_stud_ch) {
		this.pay_stud_ch = pay_stud_ch;
	}
	public Boolean getContract_dres_ch() {
		return contract_dres_ch;
	}
	public void setContract_dres_ch(Boolean contract_dres_ch) {
		this.contract_dres_ch = contract_dres_ch;
	}
	public Boolean getPay_dres_ch() {
		return pay_dres_ch;
	}
	public void setPay_dres_ch(Boolean pay_dres_ch) {
		this.pay_dres_ch = pay_dres_ch;
	}
	public Boolean getContract_make_ch() {
		return contract_make_ch;
	}
	public void setContract_make_ch(Boolean contract_make_ch) {
		this.contract_make_ch = contract_make_ch;
	}
	public Boolean getPay_make_ch() {
		return pay_make_ch;
	}
	public void setPay_make_ch(Boolean pay_make_ch) {
		this.pay_make_ch = pay_make_ch;
	}

    

    public int getPlan_no() {
        return plan_no;
    }
    public void setPlan_no(int plan_no) {
        this.plan_no = plan_no;
    }
	public String getContract_hall() {
		return contract_hall;
	}
	public void setContract_hall(String contract_hall) {
		this.contract_hall = contract_hall;
	}
	public int getPay_hall() {
		return pay_hall;
	}
	public void setPay_hall(int pay_hall) {
		this.pay_hall = pay_hall;
	}
	public String getContract_stud() {
		return contract_stud;
	}
	public void setContract_stud(String contract_stud) {
		this.contract_stud = contract_stud;
	}
	public int getPay_stud() {
		return pay_stud;
	}
	public void setPay_stud(int pay_stud) {
		this.pay_stud = pay_stud;
	}
	public String getContract_dres() {
		return contract_dres;
	}
	public void setContract_dres(String contract_dres) {
		this.contract_dres = contract_dres;
	}
	public int getPay_dres() {
		return pay_dres;
	}
	public void setPay_dres(int pay_dres) {
		this.pay_dres = pay_dres;
	}
	public String getContract_make() {
		return contract_make;
	}
	public void setContract_make(String contract_make) {
		this.contract_make = contract_make;
	}
	public int getPay_make() {
		return pay_make;
	}
	public void setPay_make(int pay_make) {
		this.pay_make = pay_make;
	}

   
}
