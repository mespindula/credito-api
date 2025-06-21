FROM openjdk:21-jdk-slim

COPY target/credito-api.jar credito-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/credito-api.jar"]