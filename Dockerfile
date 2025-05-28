FROM eclipse-temurin:17-jdk
ARG JAR_FILE=build/libs/*.jar

# prod 설정만 복사
COPY src/main/resources/application-prod.yml /app/config/

# 빌드된 JAR 복사
COPY ${JAR_FILE} /app/app.jar

# prod 프로파일 + 설정 경로 지정
ENTRYPOINT ["java","-Dspring.config.location=/app/config/","-Dspring.profiles.active=prod","-jar","/app/app.jar"]
