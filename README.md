# Library Management Service

## Team
- **Team Name:** shovel
- **Members:**
  - Alexandru Țîncu - Everything

## Project Description

Library Management Service is a SaaS that enbales library managers to keep track of all of their books.

### Key Features
- Book management

### Technical Stack
- **Backend:** Spring Boot (Java 21)
- **Database:** MongoDB
- **API:** RESTful
- **Testing:** JUnit, Mockito, Cucumber
- **Monitoring:** Prometheus, Grafana
- **Deployment:** Docker

## Contributing

All team members follow trunk-based development:
1. Create feature branch from `main`
2. Make changes and commit with clear messages
3. Create PR and request review
4. Address feedback
5. Merge after approval

# Prerequisites

For using Github Codespaces, no prerequisites are mandatory.
Follow the [./PREREQUISITES.md](./PREREQUISITES.md) instructions to configure a local virtual machine with Ubuntu, Docker, IntelliJ.

# Access the code

* Fork the code GitHub repository under your Organization
  * https://github.com/UNIBUC-PROD-ENGINEERING/service
* Clone the code repository:
  * git@github.com:YOUR_ORG_NAME/service.git

# Run code in Github Codespaces

* Make sure that the Github repository is forked under your account / Organization
* Create a new Codespace from your forked repository
* Wait for the Codespace to be up and running
* Make sure that Docker service has been started
    * ```docker ps``` should return no error
* For running / debugging directly in Visual Studio Code
  * Start the MongoDB related services
    * ```./start_mongo_only.sh```
  * Build and run the Spring Boot service
    * ```./gradlew build```
    * ```./gradlew bootRun```
* For running all services in Docker:
    * Build the Docker image of the prod-eng service
        * ```make build```
    * Start all the service containers
        * ```./start.sh```
* Use [requests.http](requests.http) to test API endpoints
* Navigation between methods (e.g. 'Go to Definition') may require:
  * ```./gradlew build``` 

NOTE: for a live demo, please check out [this youtube video](https://youtu.be/-9ePlxz03kg)

# Run/debug code in IntelliJ
* Build the code
    * IntelliJ will build it automatically
    * If you want to build it from command line and also run unit tests, run: ```./gradlew build```
* Create an IntelliJ run configuration for a Jar application
    * Add in the configuration the JAR path to the build folder `./build/libs/prod-eng-0.0.1-SNAPSHOT.jar`
* Start the MongoDB container using Docker Compose
    * ```./start_mongo_only.sh```
* Run/debug your IntelliJ run configuration
* Open in your browser:
    * http://localhost:8080/api/users

# Deploy and run the code locally as Docker instance

* Build the Docker image of the prod-eng service
    * ```make build```
* Start all the containers
    * ```./start.sh```

* Verify that all containers started, by running
  ```
  service git:(master) ✗  $ docker ps
  CONTAINER ID   IMAGE             COMMAND                  CREATED         STATUS         PORTS                                                                                          NAMES
  d0a14d57ade1   jenkins/jenkins   "/usr/bin/tini -- /u…"   5 seconds ago   Up 4 seconds   0.0.0.0:50000->50000/tcp, [::]:50000->50000/tcp, 0.0.0.0:8082->8080/tcp, [::]:8082->8080/tcp   service-jenkins-1
  d9465565ebc9   mongo-express     "/sbin/tini -- /dock…"   5 seconds ago   Up 4 seconds   0.0.0.0:8090->8081/tcp, [::]:8090->8081/tcp                                                    service-mongo-admin-ui-1
  304c29bb39ea   mongo:6.0.20      "docker-entrypoint.s…"   5 seconds ago   Up 4 seconds   0.0.0.0:27017->27017/tcp, [::]:27017->27017/tcp                                                service-mongo-1
  a74b4cb2fb58   prod-eng-img      "java -jar /prod-eng…"   5 seconds ago   Up 4 seconds   0.0.0.0:5005->5005/tcp, [::]:5005->5005/tcp, 0.0.0.0:8080->8080/tcp, [::]:8080->8080/tcp       service-prod-eng-1
  ```
* Open in your browser:
    * http://localhost:8080/api/users
* You can test other API endpoints using [requests.http](requests.http)
* You can access the MongoDB Admin UI at:
  * http://localhost:8090
  * default credentials: username `unibuc`, password `adobe`
  * database `test` contains application entities
