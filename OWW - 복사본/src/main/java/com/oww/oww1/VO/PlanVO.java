package com.oww.oww1.VO;

public class PlanVO {
	int plan_no, hall, studio, dress, makeup;
	String user_email;
	

	public int getPlan_no() {
		return plan_no;
	}
	public void setPlan_no(int plan_no) {
		this.plan_no = plan_no;
	}
	public int getHall() {
		return hall;
	}
	public void setHall(int hall) {
		this.hall = hall;
	}
	public int getStudio() {
		return studio;
	}
	public void setStudio(int studio) {
		this.studio = studio;
	}
	public int getDress() {
		return dress;
	}
	public void setDress(int dress) {
		this.dress = dress;
	}
	public int getMakeup() {
		return makeup;
	}
	public void setMakeup(int makeup) {
		this.makeup = makeup;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
}
