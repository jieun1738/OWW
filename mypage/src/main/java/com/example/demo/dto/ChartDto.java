package com.example.demo.dto;

import java.util.List;

public record ChartDto(
        List<String> labels,
        List<Long> budget,
        List<Long> paid
) {}
