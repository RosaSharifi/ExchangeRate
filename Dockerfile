FROM eclipse-temurin:17
LABEL maintainer="ramin.k92@gmail.com"
WORKDIR /app
COPY target/exchange-rate.jar /app/exchange-rate.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "exchange-rate.jar"]