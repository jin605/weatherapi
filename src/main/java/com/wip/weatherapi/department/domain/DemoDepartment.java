package com.wip.weatherapi.department.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "demo_departments")
public class DemoDepartment {

    @Id
    @Column(name = "dept_cd", nullable = false, length = 50)
    private String deptCd;

    @Column(name = "dept_nm", nullable = false, length = 100)
    private String deptNm;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "duty_desc", length = 255)
    private String dutyDesc;

    @Column(name = "up_dept_cd", length = 50)
    private String upDeptCd;

    @Column(name = "emp_cnt")
    private Integer empCnt;

    @Column(name = "mngr_nm", length = 100)
    private String mngrNm;

    @Column(name = "upd_dt", nullable = false)
    private LocalDateTime updDt;

    protected DemoDepartment() {
    }

    public String getDeptCd() {
        return deptCd;
    }

    public String getDeptNm() {
        return deptNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public String getDutyDesc() {
        return dutyDesc;
    }

    public String getUpDeptCd() {
        return upDeptCd;
    }

    public Integer getEmpCnt() {
        return empCnt;
    }

    public String getMngrNm() {
        return mngrNm;
    }

    public LocalDateTime getUpdDt() {
        return updDt;
    }
}
