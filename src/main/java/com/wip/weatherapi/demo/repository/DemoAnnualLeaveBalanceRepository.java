package com.wip.weatherapi.demo.repository;

import com.wip.weatherapi.demo.domain.DemoAnnualLeaveBalance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoAnnualLeaveBalanceRepository extends JpaRepository<DemoAnnualLeaveBalance, Long> {

    Optional<DemoAnnualLeaveBalance> findFirstByEmployeeEmployeeIdAndLeaveYear(String employeeId, int leaveYear);
}
