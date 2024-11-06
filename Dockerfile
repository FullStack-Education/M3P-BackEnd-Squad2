FROM maven:3.8-openjdk-17 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine

COPY --from=build /app/target/labpcp-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]