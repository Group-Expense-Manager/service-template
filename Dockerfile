FROM amazoncorretto:21-alpine-jdk AS builder

RUN apk --no-cache add make

WORKDIR /app

COPY . .

RUN ./gradlew build

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*[0-9].[0-9].[0-9].jar /app/app.jar

EXPOSE 4001

CMD ["java", "-Dspring.profiles.active=local", "-jar", "app.jar"]
