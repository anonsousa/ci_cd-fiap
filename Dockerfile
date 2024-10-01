FROM maven:3.9.8-eclipse-temurin-21 AS build

RUN mkdir /opt/app

COPY . /opt/app

WORKDIR /opt/app

RUN mvn clean package

FROM eclipse-temurin:21-alpine

VOLUME /tmp

RUN mkdir /opt/app

COPY --from=build /opt/app/target/api-coleta-residuos.jar /opt/app/app.jar

WORKDIR /opt/app

ENV PROFILE=dev

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/opt/app/app.jar"]
