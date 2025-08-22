package com.example.demo.vo;

import lombok.Data;

@Data 
public class MonthlyPlansVO {
    private String ym;  // "YYYY-MM"
    private int    cnt; // 월 생성 플랜 수
}
