FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/billingSoftware-0.01-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]