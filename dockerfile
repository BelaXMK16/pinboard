# Build fázis
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

# Maven wrapper + pom.xml másolása és függőségek előtöltése
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Forráskód másolása és build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Futási fázis
FROM eclipse-temurin:24-jdk
WORKDIR /app

# az elkészült jar bemásolása a builder fázisból
COPY --from=builder /app/target/*.jar app.jar

# A Spring Boot app alap portja
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]