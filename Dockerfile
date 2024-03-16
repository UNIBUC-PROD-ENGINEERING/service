FROM openjdk:21

COPY ./build/libs/triplea-0.0.1-SNAPSHOT.jar /hello/libs/hello.jar

WORKDIR /hello/libs/

CMD ["java", "-jar","/hello/libs/hello.jar"]