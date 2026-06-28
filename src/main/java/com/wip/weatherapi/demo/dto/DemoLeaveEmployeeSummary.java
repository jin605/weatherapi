package com.wip.weatherapi.demo.dto;

import com.wip.weatherapi.demo.domain.DemoEmployee;

public record DemoLeaveEmployeeSummary(
        String employeeId,
        String name,
        String departmentName
) {
    public static DemoLeaveEmployeeSummary from(DemoEmployee employee) {
        return new DemoLeaveEmployeeSummary(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getDepartmentName()
        );
    }
}
