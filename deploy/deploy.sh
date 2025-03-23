#!/bin/bash

# Check if Prometheus and Postgres services are up
if ! docker ps | grep -q prometheus || ! docker ps | grep -q postgres; then
  chmod +x ./sports-elo/deploy/deploy_infrastructure.sh
  sh sports-elo/deploy/deploy_infrastructure.sh
fi

docker pull docker.io/baaarbz/sports-elo:latest
docker run -d --restart=always --replace -p 8000:8000 --name sports-elo \
  -e POSTGRES_USER=$POSTGRES_USER \
  -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
  -e POSTGRES_URL=$POSTGRES_URL \
  -e AUTH_USERNAME=$AUTH_USERNAME \
  -e AUTH_PASSWORD=$AUTH_PASSWORD \
  baaarbz/sports-elo:latest
docker images --filter "dangling=true" -q | xargs -r docker rmi
