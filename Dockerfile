FROM openjdk:21

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n
COPY ./build/libs/triplea-0.0.1-SNAPSHOT.jar /triplea/libs/triplea.jar

WORKDIR /triplea/libs

CMD ["java", "-jar","/triplea/libs/triplea.jar"]