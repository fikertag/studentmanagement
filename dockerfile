# Use OpenJDK 17 base image
FROM openjdk:17-slim

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and install dependencies first to take advantage of Docker caching
COPY pom.xml .

# Install Maven (if not present in your OpenJDK image)
RUN apt-get update && apt-get install -y maven

# Copy the rest of the project files
COPY . .

# Build the project using Maven (this will generate the target/student-management-bot-1.0.jar)
RUN mvn clean package

# Set the environment variable (should be set dynamically or in Render dashboard)
ENV BOT_TOKEN=""

# Run the bot with the correct JAR file path
CMD ["java", "-jar", "target/student-management-bot-1.0.jar"]
