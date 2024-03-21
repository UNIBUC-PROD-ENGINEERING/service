#!/bin/bash
set -x

docker compose --profile monitoring --profile postgres --profile triplea-service down 
