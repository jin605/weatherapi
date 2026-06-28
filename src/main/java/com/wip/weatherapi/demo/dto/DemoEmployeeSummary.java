package com.wip.weatherapi.demo.dto;

import com.wip.weatherapi.demo.domain.DemoEmployee;

public record DemoEmployeeSummary(
        String employeeId,
        String loginId,
        String name,
        String departmentName,
        String positionName,
        String email,
        String phoneNumber,
        String status
) {
    public static DemoEmployeeSummary from(DemoEmployee employee) {
        return new DemoEmployeeSummary(
                employee.getEmployeeId(),
                employee.getLoginId(),
                employee.getName(),
                employee.getDepartmentName(),
                employee.getPositionName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getStatus()
        );
    }
}
