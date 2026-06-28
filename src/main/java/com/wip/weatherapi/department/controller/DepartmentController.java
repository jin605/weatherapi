package com.wip.weatherapi.department.controller;

import com.wip.weatherapi.department.dto.DepartmentResponse;
import com.wip.weatherapi.department.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public DepartmentResponse getDepartments() {
        return departmentService.getDepartments();
    }
}
