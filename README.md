# 🎸 콘서트 예약 시스템

## 📝 프로젝트 개요 및 기술 스택

이 프로젝트는 콘서트 좌석 예약 및 결제 기능을 제공하는 백엔드 시스템입니다. DDD(Domain-Driven Design) 원칙을 기반으로 설계되었으며, 높은 동시성 처리와 시스템 안정성을 목표로 합니다.

### ✨ 주요 기술 스택

| 분류         | 기술 스택                                                                                                                                                                                                                              |
| :----------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **백엔드**   | `Spring Boot 3.x` / `Java 21` <br> `QueryDSL` (복잡한 쿼리 작성) <br> `JPA` (데이터베이스 ORM) <br> `Redis` (분산락, 캐싱) <br> `MariaDB` (관계형 데이터베이스)                                                                            |
| **아키텍처** | `DDD (Domain-Driven Design)` (도메인 주도 설계) <br> `Event-Driven Architecture` (Spring Events, TransactionalEventListener를 활용한 도메인 이벤트 처리) <br> `Anti-Corruption Layer (ACL)` (PG 연동 등 외부 시스템과의 경계) |
| **모니터링** | `Micrometer` (애플리케이션 메트릭 수집) <br> `Actuator` (스프링 앱 모니터링 엔드포인트) <br> `Prometheus` (메트릭 저장 및 수집) <br> `Grafana` (메트릭 시각화 대시보드)                                                                  |
| **로깅**     | `구조화된 로깅` (JSON 형식, MDC 활용) <br> `주요 비즈니스 이벤트 로깅` (도메인 이벤트 기반, 비동기 처리) <br> `요청/응답 로깅` (필터/AOP 기반)                                                                                           |
| **테스트**   | `Locust` (분산 부하 테스트)                                                                                                                                                                                                              |
| **배포**     | `Docker` (컨테이너화) <br> `Docker Compose` (컨테이너 오케스트레이션)                                                                                                                                                                  |

## 🚀 실행 방법 및 API 사용 가이드

이 프로젝트는 Docker Compose를 사용하여 모든 서비스를 한 번에 쉽게 실행할 수 있도록 구성되어 있습니다.

### 📋 사전 준비

1.  **Java 21:** JDK 21이 설치되어 있어야 합니다.
2.  **Docker & Docker Compose:** Docker Desktop이 설치되어 있어야 합니다.
3.  **Gradle:** 프로젝트 빌드를 위해 Gradle이 설치되어 있어야 합니다.

### ⚙️ 프로젝트 빌드 및 실행

1.  **프로젝트 클론:**
    ```bash
    git clone [프로젝트_레포지토리_URL]
    cd [프로젝트_디렉토리]
    ```

2.  **스프링부트 애플리케이션 빌드:**
    Docker Compose로 컨테이너를 빌드하기 전에 스프링부트 애플리케이션의 JAR 파일을 먼저 빌드해야 합니다.
    ```bash
    ./gradlew clean build
    ```
    이 명령은 `build/libs` 디렉토리에 `.jar` 파일을 생성합니다.

3.  **Docker Compose로 모든 서비스 실행:**
    프로젝트 루트 디렉토리에 있는 `docker-compose.yml` 파일을 사용하여 모든 컨테이너를 실행합니다.
    ```bash
    docker compose up -d
    ```
    *   `spring-app`: 스프링부트 애플리케이션 (8080 포트)
    *   `mariadb`: MariaDB 데이터베이스 (33306 포트)
    *   `redis`: Redis 캐시 및 분산락 (6380 포트)
    *   `prometheus`: Prometheus 메트릭 서버 (9090 포트)
    *   `grafana`: Grafana 대시보드 (3000 포트)
    *   `master`: Locust 마스터 (8089 포트)
    *   `worker`: Locust 워커 (마스터에 연결)

4.  **서비스 중지:**
    ```bash
    docker compose down
    ```

### 🌐 API 사용 가이드

애플리케이션이 실행되면 다음 URL을 통해 API에 접근할 수 있습니다.

*   **기본 URL:** `http://localhost:8080`

#### 🔑 인증 (Authentication)

API 호출 전 인증을 통해 JWT 토큰을 발급받아야 합니다.

*   **회원가입:**
    *   `POST /api/members`
    *   **Request Body:**
        ```json
        {
          "loginId": "testuser",
          "password": "password123",
          "nickname": "테스트유저"
        }
        ```
*   **로그인:**
    *   `POST /api/auth/login`
    *   **Request Body:**
        ```json
        {
          "loginId": "testuser",
          "password": "password123"
        }
        ```
    *   **Response:**
        ```json
        {
          "token": "발급된_JWT_토큰"
        }
        ```
    *   **사용:** 발급받은 `token`을 이후 API 호출 시 `Authorization` 헤더에 `Bearer {token}` 형식으로 포함하여 전송합니다.

#### 🎫 주요 API 엔드포인트

| 기능           | HTTP Method | URI                                  | 설명                                        |
| :------------- | :---------- | :----------------------------------- | :------------------------------------------ |
| **좌석 선택**    | `POST`      | `/api/seats/select`                  | 콘서트 좌석 선택 (동시성 제어)              |
| **예약 생성**    | `POST`      | `/api/reservations`                  | 선택된 좌석으로 예약 생성                   |
| **결제 시작**    | `POST`      | `/api/payments/`                     | 예약에 대한 결제 시작 요청                  |
| **결제 취소**    | `POST`      | `/api/payments/cancel`               | 예약된 결제 취소                            |

#### 📄 API 문서 (Swagger UI)

애플리케이션이 실행되면 다음 URL에서 Swagger UI를 통해 모든 API 문서를 확인할 수 있습니다.

*   **Swagger UI:** `http://localhost:8080/swagger-ui.html`

### 📈 모니터링

애플리케이션 실행 후 다음 URL을 통해 모니터링 도구에 접근할 수 있습니다.

*   **Prometheus:** `http://localhost:9090`
    *   `Status` > `Targets` 메뉴에서 `spring-app:8080`이 `UP` 상태인지 확인합니다.
*   **Grafana:** `http://localhost:3000`
    *   **초기 로그인:** `admin` / `admin`
    *   **데이터 소스 추가:** `Configuration` > `Data Sources` > `Add data source` > `Prometheus` 선택 후 URL에 `http://prometheus:9090` 입력.
    *   **대시보드 임포트:** `Dashboards` > `Import`에서 `4701` (Spring Boot Statistics) 또는 `19004` (Spring Boot 3.x Statistics) 같은 ID를 사용하여 대시보드를 가져옵니다.

### 🧪 부하 테스트 (Locust)

Locust를 사용하여 시스템의 성능을 테스트할 수 있습니다.

*   **Locust UI:** `http://localhost:8089`
    *   접속 후 테스트할 사용자 수와 초당 요청 수를 설정하여 부하 테스트를 시작합니다.
    *   `locustfile-issue.py` 스크립트는 `api/auth/login`을 통해 토큰을 발급받고, `api/seats/select`를 호출하여 좌석 선택 기능을 테스트하도록 구성되어 있습니다.

---
