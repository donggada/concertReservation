import random
from locust import task, FastHttpUser, stats, between

stats.PERCENTILES_TO_CHART = [0.95, 0.99]


class AuthenticatedCouponIssue(FastHttpUser):
    wait_time = between(1, 3)  # 요청 사이에 1~3초 대기
    connection_timeout = 10.0
    network_timeout = 10.0

    # 사용자 초기화 시 로그인하고 토큰 저장
    def on_start(self):
        # 로그인 요청
        login_payload = {
            "loginId": f"user_{random.randint(1, 1000)}",
            "password": "password123"
        }

        # 로그인 API 호출
        response = self.client.post("api/auth/login", json=login_payload)

        # 응답에서 토큰 추출
        if response.status_code == 200:
            self.token = response.json().get("token")
            if not self.token:
                self.token = response.json().get("accessToken")  # 다른 키 이름일 수도 있음
        else:
            self.token = None
            print(f"로그인 실패: {response.status_code}, {response.text}")

    @task
    def issue_coupon(self):
        # 토큰이 없으면 요청 건너뛰기
        if not hasattr(self, "token") or self.token is None:
            return

        # 요청 헤더에 토큰 추가
        headers = {
            "Authorization": f"Bearer {self.token}"
        }

        # 쿠폰 발급 요청 페이로드
        payload = {
             "seatIdList": random.sample(range(1, 100), random.randint(1, 5))
        }

        # 헤더와 함께 API 호출
        with self.client.post("/api/seats/select", json=payload, headers=headers, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"좌석 선택 실패: {response.status_code}")