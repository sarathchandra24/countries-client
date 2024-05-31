# Stage 1: Build the application with Gradle
FROM gradle:7.2-jdk17 AS builder

WORKDIR /app

ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV SPRING_PROFILES_ACTIVE=test

COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle

# Copy the source code
COPY src ./src

# Build the application
RUN ./gradlew clean build --no-daemon

# Stage 2: Create the final lightweight image
FROM amazoncorretto:17-alpine

WORKDIR /app

# Copy the built JAR file from the Gradle stage
COPY --from=builder /app/build/libs/*.war ./application.war

# Define environment variables
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV SPRING_PROFILES_ACTIVE=test

# Expose the port your app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "application.war"]