FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests





FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/ClientHub-0.0.1-SNAPSHOT.jar app.jar
# Copia el .jar que construyó la etapa 1 (builder)

EXPOSE 8080
# Le dice a Docker que la app usa el puerto 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
# El comando que corre cuando el contenedor arranca