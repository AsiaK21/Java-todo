FROM maven:3.9.4-eclipse-temurin-17 AS build
COPY .. /app
WORKDIR /app
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app/target/asia.jar /app.jar
ENV SPRING_PROFILES_ACTIVE="docker"
ENTRYPOINT ["java", "-jar", "/app.jar"]