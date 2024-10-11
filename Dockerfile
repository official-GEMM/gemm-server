FROM openjdk:17-jdk

WORKDIR /app

COPY ./build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java"]

ENV PROFILE=${PROFILE}

CMD ["-jar","./app.jar", "--spring.profiles.active=${PROFILE}", "-Duser.timezone=Asia/Seoul"]