# Use official OpenJDK 21 image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy jar file from target folder
COPY target/e-commerce-application.jar e-commerce-application.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
