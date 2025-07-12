# Etapa 1: build con Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Instala solc (versión 0.8.x como ejemplo)
RUN apt-get update && apt-get install -y software-properties-common && \
    add-apt-repository ppa:ethereum/ethereum && \
    apt-get update && apt-get install -y solc

RUN mvn clean package -DskipTests

# Etapa 2: imagen final con JDK
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto (ajusta si tu app usa otro)
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
