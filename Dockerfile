FROM eclipse-temurin:17
LABEL maintainer="ramin.k92@gmail.com"
WORKDIR /app
COPY target/ExchangeRateService-1.0.0-SNAPSHOT.jar /app/ExchangeRateService-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ExchangeRateService-1.0.0.jar"]