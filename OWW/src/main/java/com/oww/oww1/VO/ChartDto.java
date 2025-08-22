package com.oww.oww1.VO;

import java.util.List;
import java.util.Objects;

public record ChartDto(
        List<String> labels,
        List<Long>   budget,
        List<Long>   paid
) {
    public ChartDto {
        Objects.requireNonNull(labels, "labels");
        Objects.requireNonNull(budget, "budget");
        Objects.requireNonNull(paid,   "paid");
        labels = List.copyOf(labels);
        budget = List.copyOf(budget);
        paid   = List.copyOf(paid);

        int n = labels.size();
        if (n != budget.size() || n != paid.size()) {
            throw new IllegalArgumentException("labels/budget/paid 길이가 동일해야 합니다.");
        }
    }
}
