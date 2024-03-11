FROM openjdk:11

COPY ./build/libs/hello-0.0.1-SNAPSHOT.jar /hello/libs/hello.jar
COPY ./src/main/java/ro/unibuc/hello/Player.json /hello/libs/Player.json
COPY ./src/main/java/ro/unibuc/hello/Team.json /hello/libs/Team.json
COPY ./src/main/java/ro/unibuc/hello/Game.json /hello/libs/Game.json
WORKDIR /hello/libs/

CMD ["java", "-jar","/hello/libs/hello.jar"]