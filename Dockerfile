FROM maven:4.0.0-rc-5-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY ./ ./

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/newunimol-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 23109

ENTRYPOINT [ "" ]