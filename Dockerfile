
FROM openjdk:17

WORKDIR /shop

COPY . .

RUN ./mvnw clean package -X

ENTRYPOINT ["java", "-jar", "infrastructure/target/infrastructure-0.0.1-SNAPSHOT.jar"]