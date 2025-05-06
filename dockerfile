# Use OpenJDK 17 base image
FROM openjdk:17-slim

# Set working directory inside the container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy all project files into the container
COPY . .

# Build the project using Maven
RUN mvn clean package

# Environment variable (should be set in Render dashboard, not hardcoded)
ENV BOT_TOKEN=""

# Run the bot
CMD ["java", "-cp", "target/student-management-bot-1.0.jar", "bot.BotStarter"]
