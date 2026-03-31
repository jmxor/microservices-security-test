# Stage 1: builder - build the application using Maven
FROM maven:3.9.14-eclipse-temurin-25 AS builder
WORKDIR /workspace

# Copy only the pom first to leverage Docker layer caching for dependencies
COPY pom.xml .
# If you use the Maven wrapper (mvnw), copy it and the .mvn directory too:
# COPY mvnw .
# COPY .mvn .mvn

# Pre-download dependencies (speeds up iterative builds)
RUN mvn -B -DskipTests dependency:go-offline

# Copy sources and build the app
COPY src ./src
RUN mvn -B -DskipTests package --no-transfer-progress

# Stage 2: runtime - small image with just the JAR and the JRE
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /workspace/target/*.jar app.jar

# (Optional) expose the default Spring Boot port
EXPOSE 8080

# Run as non-root: uncomment and adjust if desired and available in the base image
# RUN groupadd -r app && useradd -r -g app app && chown -R app:app /app
# USER app

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
