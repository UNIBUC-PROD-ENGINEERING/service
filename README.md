# Triple A
This project represents a video game forum, for staying up to date with the video game community. Using data from the Steam API, you can:
- Get a list of games, up to a specified limit
- Leave a review for one of these games
- See reviews for a game
- See reviews left by a user
- Add a game to your favourites
- See all favourites of a given user

All secured by JWT user authentication!

# Prerequisites

For using Github Codespaces, no prerequisites are mandatory.
Follow the [./PREREQUISITES.md](./PREREQUISITES.md) instructions to configure a local virtual machine with Ubuntu, Docker, IntelliJ.

# Run code in Github Codespaces

* Make sure that the Github repository is forked under your account / Organization
* Create a new Codespace from your forked repository
* Wait for the Codespace to be up and running
* Make sure that Docker service has been started
    * ```docker ps``` should return no error
* For running all services in docker:
    * Build the docker image of the hello world service
        * ```make build```
    * Start all the service containers
        * ```./start.sh```
* For running / debugging directly in Visual Studio Code
    * Start the Postgres related services
        * ```./start_postgres_only.sh```
    * Start the Spring Boot service by clicking `Run` button inside Visual Studio Code

NOTE: for a live demo, please check out [this youtube video](https://youtu.be/-9ePlxz03kg)

# Run/debug code in IntelliJ
* Build the code
    * IntelliJ will build it automatically
    * If you want to build it from command line and also run unit tests, run: ```./gradlew build```
* Create an IntelliJ run configuration for a Jar application
    * Add in the configuration the JAR path to the build folder `./build/libs/hello-0.0.1-SNAPSHOT.jar`
* Start the Postgres container using docker compose
    * ```docker-compose up -d postgres```
* Run/debug your IntelliJ run configuration
* Open in your browser:
    * http://localhost:8080/swagger-ui/index.html - documentation on endpoints

# Deploy and run the code locally as docker instance

* Build the docker image of the hello world service
    * ```make build```
* Start all the containers
    * ```./start.sh```

* Verify that all containers started, by running
  ```
  service git:(master) ✗  $ docker ps
  CONTAINER ID   IMAGE             COMMAND                  CREATED         STATUS         PORTS                                                                                  NAMES
  b8447103d82e   postgres:latest   "docker-entrypoint.s…"   7 seconds ago   Up 4 seconds   0.0.0.0:5432->5432/tcp, :::5432->5432/tcp                                              service-postgres_db-1
  4282da3d33a2   triplea-img       "java -jar /triplea/…"   7 seconds ago   Up 4 seconds   0.0.0.0:5005->5005/tcp, :::5005->5005/tcp, 0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   service-triplea-1
  ```
* Open in your browser:
    * [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
