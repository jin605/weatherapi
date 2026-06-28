package com.wip.weatherapi.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DemoLeaveSummary(
        BigDecimal grantedDays,
        BigDecimal carriedOverDays,
        BigDecimal adjustedDays,
        BigDecimal usedDays,
        BigDecimal scheduledDays,
        BigDecimal pendingDays,
        BigDecimal remainingDays,
        LocalDate expiresOn
) {
}
