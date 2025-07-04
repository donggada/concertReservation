FROM openjdk:21-jdk-slim

WORKDIR /app

# Gradle 빌드 결과물 복사
COPY build/libs/*.jar app.jar

# 컨테이너 실행 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]