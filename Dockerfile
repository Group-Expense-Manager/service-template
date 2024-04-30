FROM amazoncorretto:21-alpine-jdk AS builder

ENV LIB_TOKEN=${LIB_TOKEN}
ENV USERNAME=${USERNAME}


RUN apk --no-cache add make

WORKDIR /app

COPY . .

RUN ./gradlew build

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 4001

CMD ["java", "-Dspring.profiles.active=local", "-jar", "app.jar"]
