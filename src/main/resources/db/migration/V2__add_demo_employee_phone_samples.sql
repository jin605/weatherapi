UPDATE demo_employees
SET phone_number = '010-4899-8954',
    updated_at = CURRENT_TIMESTAMP
WHERE employee_id = 'SA001';

INSERT INTO demo_employees (
    employee_id,
    login_id,
    name,
    department_name,
    position_name,
    email,
    phone_number,
    status
) VALUES
    ('SA002', 'gayoung.kim', '김가영', 'AI플랫폼팀', '매니저', 'gayoung.kim@hanwha.com', '010-3092-3138', 'ACTIVE'),
    ('SA003', 'heesoo.hwang', '황희수', 'AI플랫폼팀', '매니저', 'heesoo.hwang@hanwha.com', '010-7558-7807', 'ACTIVE');

INSERT INTO demo_annual_leave_balances (
    employee_id,
    leave_year,
    granted_days,
    carried_over_days,
    adjusted_days,
    used_days,
    scheduled_days,
    pending_days,
    expires_on
) VALUES
    ('SA002', 2026, 15.0, 0.0, 0.0, 3.0, 1.0, 0.0, DATE '2026-12-31'),
    ('SA003', 2026, 15.0, 0.0, 0.0, 5.5, 0.5, 0.0, DATE '2026-12-31');
