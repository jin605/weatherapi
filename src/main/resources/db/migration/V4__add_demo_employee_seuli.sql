INSERT INTO demo_employees (
    employee_id,
    login_id,
    name,
    department_name,
    position_name,
    email,
    phone_number,
    status
) VALUES (
    'SA005',
    'seuli.lee',
    '이슬이',
    '개발팀',
    '매니저',
    'seuli.lee@hanwha.com',
    '010-8365-7511',
    'ACTIVE'
);

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
) VALUES (
    'SA005',
    2026,
    15.0,
    0.0,
    0.0,
    1.0,
    0.0,
    0.0,
    DATE '2026-12-31'
);
