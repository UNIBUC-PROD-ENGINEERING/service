#!/bin/bash
set -x

docker compose --profile mongo --profile booking-service down
