#!/bin/bash
set -x

source pre-start-monitoring.sh

docker compose --profile monitoring --profile mongo --profile hello-service up -d 
