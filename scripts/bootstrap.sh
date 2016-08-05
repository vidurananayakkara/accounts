#!/usr/bin/env bash

VERSION=1.0-SNAPSHOT

# Go to docker image location
cd $DOCKER_PATH

# Build docker image
docker build -t c5/wso2msf4j/accounts:${VERSION} .

# Run docker container
docker run -d c5/wso2msf4j/accounts:${VERSION}