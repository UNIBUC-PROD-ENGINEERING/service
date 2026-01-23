# Use a prebuilt image providing Java 21 and a minimal OS (Alpine) the app will run on inside Docker
FROM amazoncorretto:21-alpine-jdk

# Set environment variable to configure Java to open debug port 5005
ENV JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n

# Copy the locally built JAR file from the local file system to the image
COPY ./build/libs/hello-0.0.1-SNAPSHOT.jar /hello/libs/hello.jar

# Set the working directory inside the image
WORKDIR /hello/libs/

# Define the command to run the application
CMD ["java", "-jar", "/hello/libs/hello.jar"]