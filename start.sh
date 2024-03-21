#!/bin/bash
set -x

docker compose --profile postgres --profile triplea-service up -d
