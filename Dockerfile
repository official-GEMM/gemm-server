FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java"]

CMD ["-jar","./app.jar", "--spring.profiles.active=dev"]