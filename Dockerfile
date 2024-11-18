FROM docker.io/penjdk:21-jdk

USER root
RUN adduser f1elo
USER f1elo

WORKDIR /app

COPY build/libs/f1-elo-1.0.0.jar app.jar

EXPOSE 8000

ENV SPRING_PROFILES_ACTIVE=pro

CMD ["java", "-jar", "app.jar"]