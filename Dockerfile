
# Build
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY mvnw .

COPY .mvn/ .mvn/

COPY pom.xml .

COPY src ./src

# Grant access to mvnw
RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/industryapi-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]