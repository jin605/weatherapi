package com.wip.weatherapi.demo.service;

import com.wip.weatherapi.demo.domain.DemoAnnualLeaveBalance;
import com.wip.weatherapi.demo.domain.DemoEmployee;
import com.wip.weatherapi.demo.domain.DemoEmployeeLookupType;
import com.wip.weatherapi.demo.dto.DemoEmployeeLookupResponse;
import com.wip.weatherapi.demo.dto.DemoEmployeeSummary;
import com.wip.weatherapi.demo.dto.DemoLeaveBalanceResponse;
import com.wip.weatherapi.demo.dto.DemoLeaveEmployeeSummary;
import com.wip.weatherapi.demo.dto.DemoLeaveSummary;
import com.wip.weatherapi.demo.repository.DemoAnnualLeaveBalanceRepository;
import com.wip.weatherapi.demo.repository.DemoEmployeeRepository;
import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class DemoEmployeeService {

    private final DemoEmployeeRepository employeeRepository;
    private final DemoAnnualLeaveBalanceRepository leaveBalanceRepository;

    public DemoEmployeeService(
            DemoEmployeeRepository employeeRepository,
            DemoAnnualLeaveBalanceRepository leaveBalanceRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    public DemoEmployeeLookupResponse lookupEmployee(String query, DemoEmployeeLookupType lookupType) {
        DemoEmployeeLookupType resolvedType = lookupType == null ? DemoEmployeeLookupType.AUTO : lookupType;
        DemoEmployeeLookupType matchType = resolvedType == DemoEmployeeLookupType.AUTO
                ? inferLookupType(query)
                : resolvedType;

        Optional<DemoEmployee> employee = findEmployee(query, matchType);
        return employee
                .map(value -> new DemoEmployeeLookupResponse(
                        true,
                        matchType.name(),
                        DemoEmployeeSummary.from(value),
                        "사용자 본인 정보 조회"
                ))
                .orElseGet(() -> DemoEmployeeLookupResponse.notMatched(matchType.name()));
    }

    public DemoLeaveBalanceResponse getLeaveBalance(String employeeId, Integer year) {
        if (!StringUtils.hasText(employeeId)) {
            throw new IllegalArgumentException("employeeId is required.");
        }

        int targetYear = year == null ? Year.now().getValue() : year;
        DemoEmployee employee = employeeRepository.findFirstByEmployeeIdIgnoreCase(employeeId.trim())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));
        DemoAnnualLeaveBalance balance = leaveBalanceRepository
                .findFirstByEmployeeEmployeeIdAndLeaveYear(employee.getEmployeeId(), targetYear)
                .orElseThrow(() -> new IllegalArgumentException("Annual leave balance not found."));

        return new DemoLeaveBalanceResponse(
                DemoLeaveEmployeeSummary.from(employee),
                balance.getLeaveYear(),
                new DemoLeaveSummary(
                        balance.getGrantedDays(),
                        balance.getCarriedOverDays(),
                        balance.getAdjustedDays(),
                        balance.getUsedDays(),
                        balance.getScheduledDays(),
                        balance.getPendingDays(),
                        balance.getRemainingDays(),
                        balance.getExpiresOn()
                ),
                "연차 잔여량 조회",
                LocalDate.now()
        );
    }

    private Optional<DemoEmployee> findEmployee(String query, DemoEmployeeLookupType lookupType) {
        if (!StringUtils.hasText(query)) {
            return Optional.empty();
        }

        String trimmedQuery = query.trim();
        return switch (lookupType) {
            case PHONE -> findByPhone(trimmedQuery);
            case EMPLOYEE_ID -> employeeRepository.findFirstByEmployeeIdIgnoreCase(trimmedQuery);
            case EMAIL -> Optional.empty();
            case NAME -> employeeRepository.findFirstByName(trimmedQuery);
            case AUTO -> findEmployee(trimmedQuery, inferLookupType(trimmedQuery));
        };
    }

    private Optional<DemoEmployee> findByPhone(String query) {
        Optional<DemoEmployee> exactMatch = employeeRepository.findFirstByPhoneNumber(query);
        if (exactMatch.isPresent()) {
            return exactMatch;
        }

        String phoneDigits = query.replaceAll("[^0-9]", "");
        if (!StringUtils.hasText(phoneDigits)) {
            return Optional.empty();
        }
        return employeeRepository.findFirstByPhoneDigits(phoneDigits);
    }

    private DemoEmployeeLookupType inferLookupType(String query) {
        String value = query == null ? "" : query.trim();
        if (value.replaceAll("[^0-9]", "").length() >= 9) {
            return DemoEmployeeLookupType.PHONE;
        }
        if (value.toUpperCase(Locale.ROOT).startsWith("SA") || value.matches("\\d{6,}")) {
            return DemoEmployeeLookupType.EMPLOYEE_ID;
        }
        return DemoEmployeeLookupType.NAME;
    }
}
