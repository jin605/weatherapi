package com.wip.weatherapi.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "demo_annual_leave_balances")
public class DemoAnnualLeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_balance_id")
    private Long leaveBalanceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private DemoEmployee employee;

    @Column(name = "leave_year", nullable = false)
    private int leaveYear;

    @Column(name = "granted_days", nullable = false)
    private BigDecimal grantedDays;

    @Column(name = "carried_over_days", nullable = false)
    private BigDecimal carriedOverDays;

    @Column(name = "adjusted_days", nullable = false)
    private BigDecimal adjustedDays;

    @Column(name = "used_days", nullable = false)
    private BigDecimal usedDays;

    @Column(name = "scheduled_days", nullable = false)
    private BigDecimal scheduledDays;

    @Column(name = "pending_days", nullable = false)
    private BigDecimal pendingDays;

    @Column(name = "expires_on")
    private LocalDate expiresOn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected DemoAnnualLeaveBalance() {
    }

    public DemoEmployee getEmployee() {
        return employee;
    }

    public int getLeaveYear() {
        return leaveYear;
    }

    public BigDecimal getGrantedDays() {
        return grantedDays;
    }

    public BigDecimal getCarriedOverDays() {
        return carriedOverDays;
    }

    public BigDecimal getAdjustedDays() {
        return adjustedDays;
    }

    public BigDecimal getUsedDays() {
        return usedDays;
    }

    public BigDecimal getScheduledDays() {
        return scheduledDays;
    }

    public BigDecimal getPendingDays() {
        return pendingDays;
    }

    public LocalDate getExpiresOn() {
        return expiresOn;
    }

    public BigDecimal getRemainingDays() {
        return grantedDays
                .add(carriedOverDays)
                .add(adjustedDays)
                .subtract(usedDays)
                .subtract(scheduledDays)
                .subtract(pendingDays);
    }
}
