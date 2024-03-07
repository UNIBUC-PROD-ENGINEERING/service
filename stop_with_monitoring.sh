#!/bin/bash
set -x

docker compose --profile monitoring --profile mongo --profile hello-service down 
