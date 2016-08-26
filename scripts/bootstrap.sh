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

# Create docker container through docker-compose
runDockerCompose(){
    # Go to docker image location
    cd ${DOCKER_PATH}

    # Up docker-composer
    docker-compose -p ${DOCKER_COMPOSER_PROJECT_NAME} up
}

# Function to display container ip address and container id
displayContainerIdAndIpAddress(){

    # Get container id
    CONTAINER_ID=`docker-compose ps -q ${DOCKER_COMPOSER_CONTAINER_NAME}`

    # Get container ip address
    IP_ADDRESS=`docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${CONTAINER_ID}`

    # Display container ip address and container id
    echo "${1} started: [IP_ADDRESS] ${IP_ADDRESS} [CONTAINER_ID] ${CONTAINER_ID}"
}

# Function to start accounts service
startAccountService(){
    echo "Starting to run AccountsService solution..."
    runDockerCompose
}

# Start micro-services in docker containers
startAccountService
displayContainerIdAndIpAddress