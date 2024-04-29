FROM openjdk:21-jdk

COPY . /app
WORKDIR /app

RUN ./gradlew build

RUN mkdir /app

EXPOSE 8080
COPY build/libs/app.jar /app/

WORKDIR /app/

ENTRYPOINT ["java", "-jar", "app.jar"]
