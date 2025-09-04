# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Copy source code
COPY src/ src/

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean install -DskipTests

# Create directory for Firebase credentials if it doesn't exist
RUN mkdir -p /opt/render/project/src/main/resources

# Expose port
EXPOSE 10000

# Run the application
CMD ["java", "-jar", "target/ecobazaar-backend-1.0.0.jar"]


