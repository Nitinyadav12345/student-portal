# Stage 1: Build with Maven + JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run with JDK 21
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Optional: pass DB credentials via environment
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/student_portal_new
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=Nitin@2001
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

ENTRYPOINT ["java", "-jar", "app.jar"]
