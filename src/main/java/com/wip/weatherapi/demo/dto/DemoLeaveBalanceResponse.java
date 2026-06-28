package com.wip.weatherapi.demo.dto;

import java.time.LocalDate;

public record DemoLeaveBalanceResponse(
        DemoLeaveEmployeeSummary employee,
        int year,
        DemoLeaveSummary leave,
        String source,
        LocalDate asOfDate
) {
}
