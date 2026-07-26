FROM eclipse-temurin:21-jdk AS builder
LABEL authors="Naveendran"

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jdk AS ai-integration-image

WORKDIR /app

COPY --from=builder /app/target/*.jar ai-integration.jar

ENTRYPOINT ["java","-jar","ai-integration.jar"]