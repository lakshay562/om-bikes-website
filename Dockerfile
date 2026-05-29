FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN javac WebServer.java

EXPOSE 8080

CMD ["java", "WebServer"]
