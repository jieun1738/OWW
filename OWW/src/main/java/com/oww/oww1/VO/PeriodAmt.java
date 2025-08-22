package com.oww.oww1.VO;

import lombok.Data;

@Data
public class PeriodAmt {
    private String period;  // day 또는 month
    private long amount;
}
