#!/usr/bin/env bash

# ------------------------------------------------------------------------
#
#  Copyright (c) 2005-2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
#
#  WSO2 Inc. licenses this file to you under the Apache License,
#  Version 2.0 (the "License"); you may not use this file except
#  in compliance with the License.
#  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#
# ------------------------------------------------------------------------

# Change directory to the scripts directory
pushd $(dirname $0) > /dev/null
SCRIPT_PATH=$(pwd -P)
popd > /dev/null
cd ${SCRIPT_PATH}

# Exporting variables
source variables.sh

# Function to run docker images
# $1 is the docker image name
runDockerImage(){
    # Go to docker image location
    cd ${DOCKER_PATH}
    # Check for docker image
    # If the docker image exists ask user his / her choice
    if docker history -q ${1} >/dev/null 2>&1; then
        while true; do
            read -p "Docker image "${1}" exists. Do you want to re-build docker image (Y/y or N/n)?" yn
            case ${yn} in
                [Yy]* ) docker build -t ${1} .; break;;
                [Nn]* ) break;;
                * ) echo "Please answer (Y/y or N/n)";;
            esac
        done
    else
        # Build docker image
        docker build -t ${1} .
    fi

    # Run docker container
    CONTAINER_ID=`docker run -d ${1}`
    IP_ADDRESS=`docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${CONTAINER_ID}`

    echo "${1} started: [IP_ADDRESS] ${IP_ADDRESS} [CONTAINER_ID] ${CONTAINER_ID}"
}

# Function to start accounts service
startAccountService(){
    runDockerImage ${DOCKER_IMAGE_NAME}
}

# Start micro-services in docker containers
startAccountService