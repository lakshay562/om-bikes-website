FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN javac WebServer.java

EXPOSE 8080

CMD ["java", "WebServer"]
