package com.wip.weatherapi.department.repository;

import com.wip.weatherapi.department.domain.DemoDepartment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoDepartmentRepository extends JpaRepository<DemoDepartment, String> {

    List<DemoDepartment> findAllByOrderByDeptCdAsc();
}
