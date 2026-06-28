# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle gradle.properties ./

RUN chmod +x ./gradlew
RUN --mount=type=cache,target=/root/.gradle ./gradlew dependencies --no-daemon

COPY src src

RUN --mount=type=cache,target=/root/.gradle ./gradlew clean bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

ENV TZ=Asia/Seoul

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
