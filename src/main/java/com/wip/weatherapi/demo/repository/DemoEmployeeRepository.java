package com.wip.weatherapi.demo.repository;

import com.wip.weatherapi.demo.domain.DemoEmployee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DemoEmployeeRepository extends JpaRepository<DemoEmployee, String> {

    Optional<DemoEmployee> findFirstByEmployeeIdIgnoreCase(String employeeId);

    Optional<DemoEmployee> findFirstByName(String name);

    Optional<DemoEmployee> findFirstByPhoneNumber(String phoneNumber);

    @Query("""
            select e
            from DemoEmployee e
            where replace(e.phoneNumber, '-', '') = :phoneDigits
            """)
    Optional<DemoEmployee> findFirstByPhoneDigits(@Param("phoneDigits") String phoneDigits);
}
