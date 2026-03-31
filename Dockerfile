FROM maven:3.9.6-eclipse-temurin-17 AS builder
#Es como un mini sistema

WORKDIR /app
#el dierectorio una en la raiz de la imagen

COPY pom.xml .
#copia el pom.xml

RUN mvn dependency:go-offline
#pues corre ese comando pero no se que hace el comando

COPY src ./src
#copia el src del proyecto

RUN mvn clean package -DskipTests
#Crea el .jar sin correr los test





FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/ClientHub-0.0.1-SNAPSHOT.jar app.jar
# Copia el .jar que construyó la etapa 1 (builder)

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
