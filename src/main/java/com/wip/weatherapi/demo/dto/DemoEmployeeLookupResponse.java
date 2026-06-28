package com.wip.weatherapi.demo.dto;

public record DemoEmployeeLookupResponse(
        boolean matched,
        String matchType,
        DemoEmployeeSummary employee,
        String source
) {
    public static DemoEmployeeLookupResponse notMatched(String matchType) {
        return new DemoEmployeeLookupResponse(false, matchType, null, "사용자 본인 정보 조회");
    }
}
