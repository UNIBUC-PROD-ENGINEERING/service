# Prerequisites

Follow the [./PREREQUISITES.md](./PREREQUISITES.md) instructions to configure a local virtual machine with Ubuntu, Docker, IntelliJ.

# Access the code

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

# Deploy stack with monitoring
```
docker-compose --profile apps up -d
docker-compose --profile monitoring up -d
docker-compose --profile perf up -d --scale wrk-injector--perf=5
```

# Prereqs
Install loki logging driver docker plugin
```
docker plugin install grafana/loki-docker-driver:2.4.1 --alias loki --grant-all-permissions
```

# Monitoring

![Monitoring high level diagram](./docs/high-level-monitoring-diagram.png "Monitoring high level diagram")

App Metrics
- prom metrics [http://localhost:8080/actuator/prometheus)](http://localhost:8080/actuator/prometheus)

cAdvisor (container metrics exporter)
- UI [http://localhost:8081/containers](http://localhost:8081/containers)
- prom metrics [http://localhost:8081/metrics](http://localhost:8081/metrics)

Prometheus
- [http://localhost:9090/](http://localhost:9090/)

Grafana
- [http://localhost:3000/](http://localhost:3000/)

# Perf test

```
docker-compose up -d --remove-orphans --scale wrk-injector-info-perf=5
```
