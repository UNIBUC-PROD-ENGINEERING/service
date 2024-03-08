FROM openjdk:11

COPY ./build/libs/hello-0.0.1-SNAPSHOT.jar /hello/libs/hello.jar
COPY ./src/main/java/ro/unibuc/hello/Player.json /hello/libs/Player.json
WORKDIR /hello/libs/

CMD ["java", "-jar","/hello/libs/hello.jar"]