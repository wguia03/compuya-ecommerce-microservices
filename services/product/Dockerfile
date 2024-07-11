# Use OpenJDK 11 JRE Slim as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the working directory
# Adjust the COPY command to reflect the correct artifact name
COPY target/product-0.0.1-SNAPSHOT.jar /app/product-service.jar

# Command to run the application
CMD ["java", "-jar", "product-service.jar"]