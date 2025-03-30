BEASoftware is an application that allows users to store lists of todos. They could be private public or shared with a list of other users.

# Contributors

Barbu Eduard - Gr. 344
Florescu Bogdan Ilie - Gr. 343
Matei Alexandru Cristian - Gr. 343

# Functionalities

User CRUD:
--- create user when registering
--- be able to see(read) all users
--- update user details through PUT or PATCH
--- delete user from database

<<<<<<< HEAD
=======
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
>>>>>>> upstream/main




