#!/bin/bash

# Check if Prometheus and Postgres services are up
if ! docker ps | grep -q prometheus || ! docker ps | grep -q postgres; then
  ./deploy/deploy_infrastructure.sh
fi

FILE="f1-elo/src/main/resources/application-pro.yml"

sed -i.bak "s/\${POSTGRES_USER}/$POSTGRES_USER/g" $FILE
sed -i.bak "s/\${POSTGRES_PASSWORD}/$POSTGRES_PASSWORD/g" $FILE

# Remove the backup file created by sed
rm ${FILE}.bak

cd f1-elo

docker build -t f1-elo:latest .
docker run -d --restart unless-stopped -p 8000:8000 --name f1-elo f1-elo:latest
