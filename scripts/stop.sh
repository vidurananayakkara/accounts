#!/usr/bin/env bash

# Change directory to the scripts directory
pushd $(dirname $0) > /dev/null
SCRIPT_PATH=$(pwd -P)
popd > /dev/null
cd ${SCRIPT_PATH}

# Exporting variables
source variables.sh

echo "Stopping docker containers..."
docker ps -a | awk '{ print $1,$2 }' | grep ${DOCKER_IMAGE_NAME} | awk '{print $1 }' | xargs -I {} docker stop {}

echo "Removing docker containers..."
docker ps -a | awk '{ print $1,$2 }' | grep ${DOCKER_IMAGE_NAME} | awk '{print $1 }' | xargs -I {} docker rm {}