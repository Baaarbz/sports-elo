#!/bin/bash

docker compose -f infrastructure/docker-compose.yml down

docker compose -f infrastructure/docker-compose.yml up -d --build