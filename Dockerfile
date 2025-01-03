FROM openjdk:21-jdk

COPY ./build/libs/hello-0.0.1-SNAPSHOT.jar /hello/libs/hello.jar

WORKDIR /hello/libs/

CMD ["java", "-jar","/hello/libs/hello.jar"]