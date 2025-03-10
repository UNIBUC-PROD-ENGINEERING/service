#!/bin/bash
set -x

mkdir -p /workspaces/jenkins_config
docker compose --profile mongo --profile booking-service up -d 
