FROM eclipse-temurin:21-jdk
LABEL authors="Naveendran"

WORKDIR /app

COPY target/*.jar ai-integration.jar

ENTRYPOINT ["java","-jar","ai-integration.jar"]