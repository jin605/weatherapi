package com.wip.weatherapi.department.service;

import com.wip.weatherapi.department.domain.DemoDepartment;
import com.wip.weatherapi.department.dto.DepartmentResponse;
import com.wip.weatherapi.department.dto.DepartmentSummary;
import com.wip.weatherapi.department.repository.DemoDepartmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

    private final DemoDepartmentRepository departmentRepository;

    public DepartmentService(DemoDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse getDepartments() {
        List<DepartmentSummary> departments = departmentRepository.findAllByOrderByDeptCdAsc()
                .stream()
                .map(this::toSummary)
                .toList();

        return DepartmentResponse.success(departments);
    }

    private DepartmentSummary toSummary(DemoDepartment department) {
        return new DepartmentSummary(
                department.getDeptCd(),
                department.getDeptNm(),
                department.getUseYn(),
                department.getDutyDesc(),
                department.getUpDeptCd(),
                department.getEmpCnt(),
                department.getMngrNm(),
                department.getUpdDt().toString()
        );
    }
}
