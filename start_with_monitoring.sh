#!/bin/bash
set -x

source pre-start-monitoring.sh

docker compose --profile monitoring --profile postgres --profile triplea-service up -d 
