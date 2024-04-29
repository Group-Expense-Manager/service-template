FROM adoptium/openjdk:21-jdk-alpine AS builder

WORKDIR /
COPY . .

RUN ./gradlew build

FROM adoptium/openjdk:21-jre-alpine

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

WORKDIR /

ENTRYPOINT ["java", "-jar", "app.jar"]
