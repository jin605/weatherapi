# 데모 Function Calling API 설계

## 목적

챗봇이 Function Calling으로 외부 데모 API를 호출해 사내 구성원 정보와 연차 잔여량을 답변할 수 있게 한다.

이 API는 Workipedia 본 DB가 아니라 별도 데모 API DB를 사용한다. Workipedia는 `ai_tools`에 API Tool을 등록하고, AI 서버가 필요 시 해당 Tool을 선택해 호출하는 역할만 맡는다.

## 현재 Workipedia DB 기준

`src/main/resources/db` 기준으로 Workipedia 본 DB에는 아래 사용자 정보가 이미 있다.

- `users.user_id`
- `users.employee_id`
- `users.email`
- `users.nickname`
- `users.department_id`
- `departments.department_name`

하지만 아래 정보는 현재 본 DB에 없다.

- 전화번호
- 실명
- 직급/직책
- 연차 총량/사용량/잔여량

따라서 데모 API용 별도 테이블에서 위 정보를 관리하는 것이 맞다.

## API 1. 구성원 조회

### 용도

사용자가 전화번호, 사번, 이메일, 이름으로 사내 구성원을 물어볼 때 사용한다.

예시 질문:

- `010-7658-7639 누구야?`
- `SA001 누구야?`
- `김진혁 소속 알려줘`
- `mendel333@hanwha.com 누구 메일이야?`

### Endpoint

```http
GET /api/demo/employees/lookup
```

### Query Parameters

| 이름 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `query` | string | Y | 전화번호, 사번, 이메일, 이름 중 하나 |
| `lookupType` | string | N | `AUTO`, `PHONE`, `EMPLOYEE_ID`, `EMAIL`, `NAME` |

`lookupType` 기본값은 `AUTO`로 둔다.

### 응답 필드

```json
{
  "matched": true,
  "matchType": "PHONE",
  "employee": {
    "employeeId": "202500061",
    "loginId": "mendel333",
    "name": "고명진",
    "departmentName": "생보ITO1팀",
    "positionName": "프로",
    "email": "mendel333@hanwha.com",
    "phoneNumber": "010-7658-7639",
    "status": "ACTIVE"
  },
  "source": "사용자 본인 정보 조회"
}
```

### 데모 API 테이블 예시

```sql
CREATE TABLE demo_employees (
    employee_id VARCHAR(50) PRIMARY KEY,
    login_id VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    department_name VARCHAR(100) NOT NULL,
    position_name VARCHAR(100) NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(30) NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE KEY uk_demo_employees_login_id (login_id),
    UNIQUE KEY uk_demo_employees_email (email),
    KEY idx_demo_employees_phone_number (phone_number),
    KEY idx_demo_employees_name (name)
);
```

데모 목적이면 전화번호 평문 저장도 가능하지만, 운영성까지 고려하면 `phone_hash`, `phone_last4`를 추가하는 편이 좋다.

### AI Tool 등록 예시

Tool 이름:

```text
get_employee_profile
```

설명:

```text
사용자가 전화번호, 사번, 이메일, 이름으로 사내 구성원 정보를 물어볼 때 사용한다. 조회 결과로 이름, 소속, 직급, 사번, 이메일을 답변한다.
```

Parameters JSON Schema:

```json
{
  "type": "object",
  "properties": {
    "query": {
      "type": "string",
      "description": "조회할 전화번호, 사번, 이메일 또는 이름",
      "required": true
    },
    "lookupType": {
      "type": "string",
      "description": "조회 유형. 모르면 AUTO",
      "enum": ["AUTO", "PHONE", "EMPLOYEE_ID", "EMAIL", "NAME"],
      "required": false
    }
  }
}
```

## API 2. 연차 잔여량 조회

### 용도

사용자가 본인 또는 특정 구성원의 남은 연차를 물어볼 때 사용한다.

예시 질문:

- `내 연차 몇 개 남았어?`
- `올해 사용한 연차 알려줘`
- `고명진 연차 잔여량 알려줘`
- `SA001 연차 현황 조회해줘`

### Endpoint

```http
GET /api/demo/leaves/balance
```

### Query Parameters

| 이름 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `query` | string | N | 사번, 이름, 이메일. 없으면 현재 사용자 기준 |
| `employeeId` | string | N | 사번을 명확히 아는 경우 |
| `year` | number | N | 조회 연도. 없으면 현재 연도 |

Function Calling에서는 `query` 하나로 시작하고, 나중에 정확도가 필요하면 `employeeId`를 추가하는 식이 편하다.

### 응답 필드

```json
{
  "employee": {
    "employeeId": "202500061",
    "name": "고명진",
    "departmentName": "생보ITO1팀"
  },
  "year": 2026,
  "leave": {
    "grantedDays": 15.0,
    "carriedOverDays": 1.0,
    "adjustedDays": 0.0,
    "usedDays": 4.0,
    "scheduledDays": 1.0,
    "pendingDays": 0.5,
    "remainingDays": 10.5,
    "expiresOn": "2026-12-31"
  },
  "source": "연차 잔여량 조회",
  "asOfDate": "2026-06-28"
}
```

### 데모 API 테이블 예시

잔여량만 보여줄 거면 아래 테이블 하나로 충분하다.

```sql
CREATE TABLE demo_annual_leave_balances (
    leave_balance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL,
    leave_year INT NOT NULL,
    granted_days DECIMAL(5,2) NOT NULL DEFAULT 0,
    carried_over_days DECIMAL(5,2) NOT NULL DEFAULT 0,
    adjusted_days DECIMAL(5,2) NOT NULL DEFAULT 0,
    used_days DECIMAL(5,2) NOT NULL DEFAULT 0,
    scheduled_days DECIMAL(5,2) NOT NULL DEFAULT 0,
    pending_days DECIMAL(5,2) NOT NULL DEFAULT 0,
    expires_on DATE NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_demo_leave_employee
        FOREIGN KEY (employee_id)
        REFERENCES demo_employees (employee_id),
    CONSTRAINT uk_demo_leave_employee_year
        UNIQUE (employee_id, leave_year)
);
```

나중에 연차 신청 내역까지 보여주려면 별도 상세 테이블을 추가한다.

```sql
CREATE TABLE demo_annual_leave_usages (
    leave_usage_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL,
    leave_type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    used_days DECIMAL(5,2) NOT NULL,
    reason VARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_demo_leave_usage_employee
        FOREIGN KEY (employee_id)
        REFERENCES demo_employees (employee_id),
    KEY idx_demo_leave_usage_employee_date (employee_id, start_date, end_date)
);
```

### AI Tool 등록 예시

Tool 이름:

```text
get_annual_leave_balance
```

설명:

```text
사용자가 본인 또는 특정 구성원의 남은 연차, 사용 연차, 올해 연차 현황을 물어볼 때 사용한다. 이름이나 사번이 있으면 해당 구성원을 조회하고, 없으면 현재 사용자 기준으로 조회한다.
```

Parameters JSON Schema:

```json
{
  "type": "object",
  "properties": {
    "query": {
      "type": "string",
      "description": "조회할 사용자 이름, 사번 또는 이메일. 본인 연차를 묻는 경우 비워둘 수 있다.",
      "required": false
    },
    "employeeId": {
      "type": "string",
      "description": "조회할 사번",
      "required": false
    },
    "year": {
      "type": "number",
      "description": "조회 연도. 없으면 현재 연도",
      "required": false
    }
  }
}
```

## 챗봇 답변 포맷

구성원 조회 답변 예시:

```text
해당 번호(010-7658-7639)는 고명진 님(아이디: mendel333)으로 조회됩니다.

- 소속: 생보ITO1팀
- 직급: 프로
- 사번: 202500061
- 이메일: mendel333@hanwha.com

[출처: 사용자 본인 정보 조회]
```

연차 조회 답변 예시:

```text
고명진 님의 2026년 연차 현황입니다.

- 총 부여 연차: 15일
- 이월 연차: 1일
- 사용 연차: 4일
- 예정 연차: 1일
- 결재 대기 연차: 0.5일
- 남은 연차: 10.5일

[출처: 연차 잔여량 조회]
```

## 구현 순서

1. 데모 API DB에 `demo_employees`, `demo_annual_leave_balances` 테이블 생성
2. 샘플 직원/연차 데이터 insert
3. 데모 API에 `GET /api/demo/employees/lookup` 추가
4. 데모 API에 `GET /api/demo/leaves/balance` 추가
5. Workipedia 관리자 화면에서 HTTP API Tool 등록
6. `TOOL_ALLOWED_HOSTS`에 데모 API host 추가
7. 챗봇에서 직원 조회/연차 조회 질문으로 Function Calling 동작 확인

## 주의점

- 개인정보성 API이므로 실제 운영에서는 응답 필드를 최소화하고 권한 체크가 필요하다.
- 데모 API라도 전화번호, 이메일을 전체 공개할지 마스킹할지 기준을 정해야 한다.
- 연차 API는 기본적으로 `내 연차` 조회를 우선 지원하고, 타인 연차 조회는 관리자 권한이 있을 때만 허용하는 것이 안전하다.
- LLM이 임의의 사람 정보를 계속 조회하지 않도록 요청자 정보와 권한을 BE 쪽에서 검증하는 구조가 좋다.
