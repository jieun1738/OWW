package com.oww.oww1.VO;

public record GoalDto(long targetAmount, long savedAmount) {

    public GoalDto {
        // 음수 방지
        targetAmount = Math.max(0, targetAmount);
        savedAmount  = Math.max(0, savedAmount);
    }

    /** 남은 금액 */
    public long remaining() {
        return Math.max(0, targetAmount - savedAmount);
    }

    /** 진행률 (0~100) */
    public int percent() {
        return targetAmount > 0
                ? (int) Math.min(100, Math.round(savedAmount * 100.0 / targetAmount))
                : 0;
    }

    /** 목표 달성 여부 */
    public boolean achieved() {
        return targetAmount > 0 && savedAmount >= targetAmount;
    }
}
