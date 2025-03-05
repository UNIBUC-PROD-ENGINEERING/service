## TODO

- [ ] Once you forked the repo and decided on your project, update your service's README.md to include a detailed description of your service. This should be 1-2 paragraphs outlining what you aim to deliver, both in terms of features and also architecture, integration, etc.
- [ ] A complete demo of the lab project presented by each team, including:
 - What does the project do, how is it used
 - Technology stack (code and infrastructure)
 - Deployment, Monitoring, Alerting, Logging

Use the following naming convention for your branches:
e.g. feature/add-login
e.g. bugfix/fix-login
Use clear commit messages:
e.g. [DOC] Add details to Readme.md
e.g. [API] Add login feature

Reduce all commits to 1 on your feature branch before merging the PR (using rebase and squash).
Each PR must be reviewed and approved by at least one other team member before being merged into the main branch.

- [ ] Implement basic application functionality until next Lab
- [ ] Implement at least one complete functionality, involving both the API and the database
- [ ] We dedicate two labs for implementing the complete application functionality

https://manelevtm.ro/

<br> 

Entitati: 

Artisti
  - Date artist1
    - Poze
    - Descriere 
    - Preturi
  - Date artist1
  - Date artist1

    
Evenimente
  - ArtistID
    - Eveniment1
      - StatusEvent
      -  Data
      -  Locatia
      -  Durata
    - Eveniment2


SocialMedia
- Facebook
- Instagram
- Tiktok
- Youtube
- Mail
- Telefon
- Adresa

<br>

CRUD Eveniment
CRUD Social Media
CRUD Artist

<br>

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
* Check java version
  * ```java -version``` should return 21
  * Validate Java version is properly configured in devcontainer.json
  * If the correct Java version is set in devcontainer.json, but the command returns a different version:
    * Open Command Palette (Ctrl+Shift+P) and run `Rebuild Container`
    * Wait for the container to be rebuilt, and if the Java version is still incorrect, try a full rebuild or set it manually in the terminal:
      * ```sdk default java 21.0.5-ms```
* Make sure that Docker service has been started
    * ```docker ps``` should return no error
* For running all services in docker:
    * Build the docker image of the hello world service
        * ```make build```
    * Start all the service containers
        * ```./start.sh```
* For running / debugging directly in Visual Studio Code
  * Build and run the Spring Boot service
    * ```./gradlew build```
    * ```./gradlew bootRun```
  * Start the MongoDB related services
      * ```./start_mongo_only.sh```
* Use [requests.http](requests.http) to test API endpoints
* Navigation between methods (e.g. 'Go to Definition') may require:
  * ```./gradlew build``` 

NOTE: for a live demo, please check out [this youtube video](https://youtu.be/-9ePlxz03kg)

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
    * ```./start.sh```

* Verify that all containers started, by running
  ```
  service git:(master) ✗  $ docker ps
  CONTAINER ID   IMAGE           COMMAND                  CREATED         STATUS         PORTS                      NAMES
  c1d05dddd3fe   mongo:5.0.2     "docker-entrypoint.s…"   6 seconds ago   Up 5 seconds   0.0.0.0:27017->27017/tcp   service_mongo_1
  e90bb406c139   hello-img       "java -jar /hello/li…"   6 seconds ago   Up 5 seconds   0.0.0.0:8080->8080/tcp     service_hello_1
  411475a7b596   mongo-express   "tini -- /docker-ent…"   6 seconds ago   Up 2 seconds   0.0.0.0:8090->8081/tcp     service_mongo-admin-ui_1
  ```
* Open in your browser:
    * http://localhost:8080/hello-world
    * http://localhost:8080/info
* You can test other API endpoints using [requests.http](requests.http)
* You can access the MongoDB Admin UI at:
  * http://localhost:8090 
