# Stage 1: build
# Start with a Maven image that includes JDK 21
FROM maven:3.9.8-amazoncorretto-21 AS build

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

ENV DATABASE_HOST=dbphungsiuvip.c3ye2y2k6vcc.ap-southeast-1.rds.amazonaws.com
ENV DATABASE_USERNAME=admin
ENV DATABASE_PASSWORD=fCV8ubDqsmwjmiUnikwG
ENV DATABASE_PORT=3306
ENV ALLOWED_ORIGINS=https://phungvipfrontend.vercel.app/
ENV BASE_URL=https://besiuvip.io.vn/

# Build source code with maven
RUN mvn package -DskipTests

# Extract JAR layers
RUN java -Djarmode=layertools -jar target/*.jar extract

# Stage 2: Run Optimized JAR
FROM eclipse-temurin:21-jre AS runtime
FROM amazoncorretto:21.0.4

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
