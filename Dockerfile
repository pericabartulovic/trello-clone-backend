# Use Maven image to build the app
FROM maven:3.8.1-openjdk-17-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy pom.xml and download dependencies (this will cache the dependencies layer)
COPY pom.xml . 
RUN mvn dependency:go-offline

# Copy the source code
COPY src /app/src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller image to run the app (JRE)
FROM openjdk:17-jre-slim

# Copy the built jar file from the build image
COPY --from=build /app/target/trello-0.0.1-SNAPSHOT.jar /app/trello-0.0.1-SNAPSHOT.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "/app/trello-0.0.1-SNAPSHOT.jar"]
