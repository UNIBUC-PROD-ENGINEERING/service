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
* For running all services in docker:
    * Build the docker image of the hello world service
        * ```make build```
    * Start all the service containers
        * ```./start.sh```
* For running / debugging directly in Visual Studio Code
    * Start the MongoDB related services
        * ```./start_mongo_only.sh```
    * Start the Spring Boot service by clicking `Run` button inside Visual Studio Code

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
* You can access the MongoDB Admin UI at:
  * http://localhost:8090 

# BookWish

BookWish is a web application designed to facilitate the management of books, authors, and readers in a digital library. The system allows users to perform various tasks such as adding, updating, and deleting information related to books, authors, and readers, as well as managing wishlists.

# Key features 
Book Management 
* User can add new books to the library, providing details such as title, publication year, and author.
* Books can be deleted from the system if necessary.
* User can retrieve a list of books sorted in descending order based on the number of readers each book has.
* User can retrieve a list of books that have been read by a specific reader.
* User can retrieve a list of books written by a specific author

Readers Management 
* User can add a new reader to the library, providing necessary details such as name and email.
* User can update the information of an existing reader.
* User can delete a reader from the system, but only if the reader has not read any books and does not have any books added to the wishlist
* Users can retrieve a list of all readers registered in the online library system.

Authors Management 
* User can add a new author to the online library system, providing necessary details such as name and nationality.
* User can update the information of an existing author.
* User can delete an author from the system, but only if the author has not written any books.

Wishlist Management
* User can retrieve the wishlist of a specific reader
* User can add a book to the wishlist of a specific reader
* Users can remove a book from the wishlist of a specific reader 

# Database 

![image](https://github.com/333-Prod-Engineering/service/assets/93870739/0fd54934-7b2d-4b18-b15d-85ae6fdd06f1)

# Endpoints
Author:
* POST/authors - add a new author to the system
* PUT/authors/{author_id} - update the information of an existing author
* DELETE/authors/{author_id} - delete an author from the system only if they haven't written any books

Reader:
* POST/readers - add a new reader to the system
* PUT/readers/{reader_id} - update the information of an existing reader
* DELETE/readers/{reader_id} - delete a reader from the system only if they haven't read any books and don't have any books added to the wishlist
* GET/readers - return all readers of the online library

Book:
* POST/books - add a new book to the system
* POST/books/{book_id}/readers/{reader_id} - add a new reader to a book
* DELETE/books/{book_id} - delete a book from the system
* GET/books/readers - return books in the order of most read
* GET/books/readers/{reader_id} - return all books read by a reader
* GET/books/authors/{author_id} - return all books by an author

Wishlist:
* GET/wishlist/readers/{reader_id} - return the wishlist of a user
* POST/wishlist/readers/{reader_id}/books/{book_id} - add a book to a user's wishlist
* DELETE/wishlist/readers/{reader_id}/books/{book_id} - remove a book from a user's wishlist
