package com.wip.weatherapi.department.dto;

import java.util.List;

public record DepartmentResponse(
        String resultCode,
        String resultMsg,
        int totalCount,
        List<DepartmentSummary> data
) {
    public static DepartmentResponse success(List<DepartmentSummary> departments) {
        return new DepartmentResponse(
                "0000",
                "SUCCESS",
                departments.size(),
                departments
        );
    }
}
