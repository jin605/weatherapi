ALTER TABLE demo_annual_leave_balances
    DROP CONSTRAINT fk_demo_leave_employee;

UPDATE demo_annual_leave_balances
SET employee_id = 'SA004',
    updated_at = CURRENT_TIMESTAMP
WHERE employee_id = '202500061';

UPDATE demo_employees
SET employee_id = 'SA004',
    updated_at = CURRENT_TIMESTAMP
WHERE employee_id = '202500061';

ALTER TABLE demo_annual_leave_balances
    ADD CONSTRAINT fk_demo_leave_employee
        FOREIGN KEY (employee_id)
        REFERENCES demo_employees (employee_id);
