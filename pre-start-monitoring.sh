#!/bin/bash
set -x

mkdir -p infrastructure/prometheus/durable infrastructure/loki/durable
chmod a+w infrastructure/prometheus/durable infrastructure/loki/durable

docker plugin install grafana/loki-docker-driver:3.3.2 --alias loki --grant-all-permissions
