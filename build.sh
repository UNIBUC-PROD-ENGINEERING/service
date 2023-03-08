#!/bin/bash -e

./gradlew clean test build

docker-compose down

docker-compose build --no-cache

docker-compose up