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

echo "Stopping docker containers for accounts service ..."
docker ps -a | awk '{ print $1,$2 }' | grep ${DOCKER_IMAGE_NAME_ACCOUNT_SERVICE} | awk '{print $1 }' | xargs -I {} docker stop {}

echo "Removing docker containers for accounts service ..."
docker ps -a | awk '{ print $1,$2 }' | grep ${DOCKER_IMAGE_NAME_ACCOUNT_SERVICE} | awk '{print $1 }' | xargs -I {} docker rm {}

echo "Stopping docker containers for data service ..."
docker ps -a | awk '{ print $1,$2 }' | grep ${DOCKER_IMAGE_NAME_DATA_SERVICE} | awk '{print $1 }' | xargs -I {} docker stop {}

echo "Removing docker containers for data service ..."
docker ps -a | awk '{ print $1,$2 }' | grep ${DOCKER_IMAGE_NAME_DATA_SERVICE} | awk '{print $1 }' | xargs -I {} docker rm {}
