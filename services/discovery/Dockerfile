# Use OpenJDK 11 JRE Slim as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the working directory
COPY target/discovery-0.0.1-SNAPSHOT.jar /app/discovery-server.jar

# Command to run the application
CMD ["java", "-jar", "discovery-server.jar"]