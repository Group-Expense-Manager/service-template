FROM openjdk:21-jdk

WORKDIR /app
COPY . .

RUN ./gradlew build

FROM openjdk:21-jre

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar"]
