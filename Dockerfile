FROM docker.io/openjdk:25-jdk

LABEL org.opencontainers.image.authors="github.com/baaarbz","linkedin.com/in/eduardobarbosatarrio/"

USER root
RUN adduser sportselo
USER sportselo

WORKDIR /app

COPY build/libs/sports-elo-3.0.2.jar app.jar

EXPOSE 8000

ENV SPRING_PROFILES_ACTIVE=pro

CMD ["java", "-jar", "-Dspring.datasource.url=${POSTGRES_URL}", "-Dspring.datasource.username=${POSTGRES_USER}", "-Dspring.datasource.password=${POSTGRES_PASSWORD}", "-Dauth.username=${AUTH_USERNAME}", "-Dauth.password=${AUTH_PASSWORD}", "app.jar"]