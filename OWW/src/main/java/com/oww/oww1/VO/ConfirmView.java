package com.oww.oww1.VO;

import lombok.Data;

@Data
public class ConfirmView {
    private int planNo;     // 확정건 조회 시
    private int packageNo;  // 패키지면 패키지 번호, DIY면 9999
    private String hallName;
    private String studioName;
    private String dressName;
    private String makeupName;
    private int totalCost;

    // 화면 요약용
    public String getSummary(){
        return String.join(" / ",
            safe(hallName), safe(studioName), safe(dressName), safe(makeupName));
    }
    private String safe(String s){ return (s==null||s.isBlank()) ? "-" : s; }
}
