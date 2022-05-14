FROM openjdk:11
COPY ./build/libs/slots-0.0.1-SNAPSHOT.jar /slots/libs/slots.jar
WORKDIR /slots/libs/
CMD ["java", "-jar", "/slots/libs/slots.jar"]
