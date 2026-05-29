# Use an official lightweight Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the cloud container
WORKDIR /app

# Copy all files from your GitHub repository into the container
COPY . .

# Compile your Java server code
RUN javac WebServer.java

# Expose port 8080 so the internet can access it
EXPOSE 8080

# Run the Java server when the container starts
CMD ["java", "WebServer"]
