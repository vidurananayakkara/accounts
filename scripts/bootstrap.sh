#!/usr/bin/env bash

# Change directory to the scripts directory
pushd $(dirname $0) > /dev/null
SCRIPT_PATH=$(pwd -P)
popd > /dev/null
cd ${SCRIPT_PATH}

# Exporting variables
source variables.sh

# Go to docker image location
cd ${DOCKER_PATH}

# Build docker image
docker build -t ${DOCKER_IMAGE_NAME} .

# Run docker container
docker run -d ${DOCKER_IMAGE_NAME}