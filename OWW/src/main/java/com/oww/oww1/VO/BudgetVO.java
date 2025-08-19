package com.oww.oww1.VO;

public class BudgetVO {
	
	String user_email;
	
	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	///////////////////////////////////////
	////토큰에서 email///////////////////
	
	
	}

	int budget_no, hall, studio, dress, makeup;
	int dvd, makeup_parents, ring, hair, skincare, hanbok, honeymoon, invitation;

	
	public int getBudget_no () {
		return budget_no;
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

	public int getDvd() {
		return dvd;
	}

	public void setDvd(int dvd) {
		this.dvd = dvd;
	}

	public int getMakeup_parents() {
		return makeup_parents;
	}

	public void setMakeup_parents(int makeup_parents) {
		this.makeup_parents = makeup_parents;
	}

	public int getRing() {
		return ring;
	}

	public void setRing(int ring) {
		this.ring = ring;
	}

	public int getHair() {
		return hair;
	}

	public void setHair(int hair) {
		this.hair = hair;
	}

	public int getSkincare() {
		return skincare;
	}

	public void setSkincare(int skincare) {
		this.skincare = skincare;
	}

	public int getHanbok() {
		return hanbok;
	}

	public void setHanbok(int hanbok) {
		this.hanbok = hanbok;
	}

	public int getHoneymoon() {
		return honeymoon;
	}

	public void setHoneymoon(int honeymoon) {
		this.honeymoon = honeymoon;
	}

	public int getInvitation() {
		return invitation;
	}

	public void setInvitation(int invitation) {
		this.invitation = invitation;
	}

}
