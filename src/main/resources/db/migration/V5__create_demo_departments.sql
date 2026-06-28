CREATE TABLE demo_departments (
    dept_cd VARCHAR(50) PRIMARY KEY,
    dept_nm VARCHAR(100) NOT NULL,
    use_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
    duty_desc VARCHAR(255),
    up_dept_cd VARCHAR(50),
    emp_cnt INT,
    mngr_nm VARCHAR(100),
    upd_dt TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_demo_departments_use_yn ON demo_departments (use_yn);
CREATE INDEX idx_demo_departments_up_dept_cd ON demo_departments (up_dept_cd);

INSERT INTO demo_departments (
    dept_cd,
    dept_nm,
    use_yn,
    duty_desc,
    up_dept_cd,
    emp_cnt,
    mngr_nm,
    upd_dt
) VALUES
    ('D-0010', '개발1팀', 'Y', '백엔드 API 개발 및 공통 플랫폼 운영', 'D-0001', 8, '김개발', TIMESTAMP '2026-06-25 09:10:00'),
    ('D-0020', '개발2팀', 'Y', '프론트엔드 서비스 개발 및 사용자 경험 개선', 'D-0001', 7, '박프론트', TIMESTAMP '2026-06-25 09:20:00'),
    ('D-0030', '운영지원팀', 'Y', '서비스 운영, 장애 대응, 배포 지원', 'D-0001', 6, '이운영', TIMESTAMP '2026-06-25 09:30:00'),
    ('D-0040', '인사문화팀', 'Y', '채용, 평가, 조직문화, 온보딩', 'D-0002', 5, '최인사', TIMESTAMP '2026-06-25 09:40:00'),
    ('D-0050', '재무회계팀', 'Y', '예산, 정산, 회계 결산, 비용 관리', 'D-0002', 4, '정재무', TIMESTAMP '2026-06-25 09:50:00'),
    ('D-0060', '마케팅팀', 'Y', '브랜드 캠페인, 콘텐츠 기획, 리드 확보', 'D-0003', 6, '한마케팅', TIMESTAMP '2026-06-25 10:00:00'),
    ('D-0070', '영업1팀', 'Y', '신규 고객 발굴 및 B2B 세일즈', 'D-0003', 9, '오영업', TIMESTAMP '2026-06-25 10:10:00'),
    ('D-0080', '고객성공팀', 'Y', '고객 온보딩, VOC 관리, 사용 활성화', 'D-0003', 7, '서고객', TIMESTAMP '2026-06-25 10:20:00'),
    ('D-0090', '보안인프라팀', 'Y', '클라우드 인프라, 보안 정책, 접근 제어', 'D-0001', 5, '문보안', TIMESTAMP '2026-06-25 10:30:00'),
    ('D-0100', '데이터분석팀', 'Y', '데이터 마트, 지표 분석, 리포팅 자동화', 'D-0001', 6, '강데이터', TIMESTAMP '2026-06-25 10:40:00');
