# Use a lightweight OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the location of the app jar inside the container
ARG JAR_FILE=target/auth-course-0.0.1-SNAPSHOT.jar

# Copy the JAR into the container
COPY ${JAR_FILE} app.jar

# Expose port 8080 (or whichever your Spring app runs on)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
