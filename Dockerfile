# Etapa 1: Construcción
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Copiar todo el proyecto
COPY . .

# Compilar sin ejecutar tests y sin el plugin de Web3j
RUN mvn clean package -DskipTests -Pno-solidity

# Etapa 2: Imagen final solo con el JAR
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
