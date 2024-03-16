#!/bin/bash
set -x

docker compose --profile monitoring --profile postgres --profile hello-service down 
