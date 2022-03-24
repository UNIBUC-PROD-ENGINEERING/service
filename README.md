# Prerequisites

Follow the [./PREREQUISITES.md](./PREREQUISITES.md) instructions to configure a local virtual machine with Ubuntu, Docker, IntelliJ.

# Access the code
test
* Fork the code GitHub repository under your Organization
  * https://github.com/UNIBUC-PROD-ENGINEERING/service
* Clone the code repository:
  * git@github.com:YOUR_ORG_NAME/service.git


# Run/debug code in IntelliJ
* Build the code
    * IntelliJ will build it automatically
    * If you want to build it from command line and also run unit tests, run: ```./gradlew build```
* Create an IntelliJ run configuration for a Jar application
    * Add in the configuration the JAR path to the build folder `./build/libs/hello-0.0.1-SNAPSHOT.jar`
* Start the MongoDB container using docker compose
    * ```docker-compose up -d mongo```
* Run/debug your IntelliJ run configuration
* Open in your browser:
    * http://localhost:8080/hello-world
    * http://localhost:8080/info

# Deploy and run the code locally as docker instance

* Build the docker image of the hello world service
    * ```make build```
* Start all the containers
    * ```docker-compose up -d```

* Verify that all containers started, by running
     ```
service git:(main)  $ docker ps
CONTAINER ID   IMAGE           COMMAND                  CREATED             STATUS             PORTS                                           NAMES
77edd8b15b19   mongo-express   "tini -- /docker-ent…"   57 minutes ago      Up 57 minutes      0.0.0.0:8090->8081/tcp, :::8090->8081/tcp       service_mongo-admin-ui_1
9edd1afa25fd   mongo:5.0.2     "docker-entrypoint.s…"   About an hour ago   Up About an hour   0.0.0.0:27017->27017/tcp, :::27017->27017/tcp   service_mongo_1
     ```
* Open in your browser:
    * http://localhost:8080/hello-world
    * http://localhost:8080/info
* You can access the MongoDB Admin UI at:
  * http://localhost:8090 
