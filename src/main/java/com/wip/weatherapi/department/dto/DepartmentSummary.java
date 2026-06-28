package com.wip.weatherapi.department.dto;

public record DepartmentSummary(
        String deptCd,
        String deptNm,
        String useYn,
        String dutyDesc,
        String upDeptCd,
        Integer empCnt,
        String mngrNm,
        String updDt
) {
}
