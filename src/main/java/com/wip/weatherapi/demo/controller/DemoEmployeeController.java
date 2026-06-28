package com.wip.weatherapi.demo.controller;

import com.wip.weatherapi.demo.domain.DemoEmployeeLookupType;
import com.wip.weatherapi.demo.dto.DemoEmployeeLookupResponse;
import com.wip.weatherapi.demo.dto.DemoLeaveBalanceResponse;
import com.wip.weatherapi.demo.service.DemoEmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoEmployeeController {

    private final DemoEmployeeService demoEmployeeService;

    public DemoEmployeeController(DemoEmployeeService demoEmployeeService) {
        this.demoEmployeeService = demoEmployeeService;
    }

    @GetMapping("/employees/lookup")
    public DemoEmployeeLookupResponse lookupEmployee(
            @RequestParam String query,
            @RequestParam(defaultValue = "AUTO") DemoEmployeeLookupType lookupType
    ) {
        return demoEmployeeService.lookupEmployee(query, lookupType);
    }

    @GetMapping("/leaves/balance")
    public DemoLeaveBalanceResponse getLeaveBalance(
            @RequestParam String employeeId,
            @RequestParam(required = false) Integer year
    ) {
        return demoEmployeeService.getLeaveBalance(employeeId, year);
    }
}
