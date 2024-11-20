#!/bin/bash

# Check if Prometheus and Postgres services are up
if ! docker ps | grep -q prometheus || ! docker ps | grep -q postgres; then
  chmod +x ./f1-elo/deploy/deploy_infrastructure.sh
  ./f1-elo/deploy/deploy_infrastructure.sh
fi

docker pull docker.io/baaarbz/f1-elo:latest
docker run -d --restart=always --replace -p 8000:8000 --name f1-elo \
  -e POSTGRES_USER=$POSTGRES_USER \
  -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
  -e POSTGRES_URL=$POSTGRES_URL \
  baaarbz/f1-elo:latest
docker images --filter "dangling=true" -q | xargs -r docker rmi
