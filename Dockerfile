FROM docker.io/openjdk:21-jdk

USER root
RUN adduser f1elo
USER f1elo

WORKDIR /app

COPY build/libs/f1-elo-1.0.0.jar app.jar

EXPOSE 8000

ARG POSTGRES_URL
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD

ENV SPRING_PROFILES_ACTIVE=pro

CMD ["java", "-jar", "-Dspring.datasource.url=${POSTGRES_URL}", "-Dspring.datasource.username=${POSTGRES_USER}", "-Dspring.datasource.password=${POSTGRES_PASSWORD}", "app.jar"]