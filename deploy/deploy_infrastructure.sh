#!/bin/bash

docker stop -a

docker compose -f sports-elo/deploy/infrastructure/docker-compose.yml up -d --build

while ! docker compose -f sports-elo/deploy/infrastructure/docker-compose.yml ps | grep -q "Up"; do
  echo "Waiting for services to be up..."
  sleep 1
done