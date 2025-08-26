package com.oww.oww1.VO;

import lombok.Data;

/**
 * 예산 폼 (사이드바 4개 항목 중심)
 * - 모든 필드는 기본 0L로 시작
 * - setter에서 null/음수 들어와도 0으로 보정
 * - total4()로 합계 쉽게 계산
 */
@Data
public class BudgetForm {
private Long weddingHall;
private Long studio;
private Long dress;
private Long makeup;
public Long getAmount() {
	// TODO Auto-generated method stub
	
	long result = weddingHall+studio+dress+makeup;
	
	return result;
}

	/*
	 * @NotNull @PositiveOrZero private Long weddingHall = 0L;
	 * 
	 * @NotNull @PositiveOrZero private Long studio = 0L;
	 * 
	 * @NotNull @PositiveOrZero private Long dress = 0L;
	 * 
	 * @NotNull @PositiveOrZero private Long makeup = 0L;
	 * 
	 * // ---- 보정 setter (바인딩 시 안전) public void setWeddingHall(Long v) {
	 * this.weddingHall = safe(v); } public void setStudio(Long v) { this.studio =
	 * safe(v); } public void setDress(Long v) { this.dress = safe(v); } public void
	 * setMakeup(Long v) { this.makeup = safe(v); }
	 * 
	 * public Long getWeddingHall() { return weddingHall; } public Long getStudio()
	 * { return studio; } public Long getDress() { return dress; } public Long
	 * getMakeup() { return makeup; }
	 * 
	 * // 합계(사이드바 4개 항목) public long total4() { return safe(weddingHall) +
	 * safe(studio) + safe(dress) + safe(makeup); }
	 * 
	 * private static long safe(Long v) { return (v == null || v < 0) ? 0L : v; }
	 */
}
