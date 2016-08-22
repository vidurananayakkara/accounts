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

# check if the user provided an argument to scale up
if [ -z $1 ]; then
    echo "No figure mentioned to scale"
    exit 1
fi

# Check if the user entered a number to scale the container
regex='^[0-9]+$'
if ! [[ $1 =~ $regex ]] ; then
   echo "error: Scale should be a number !!!" >&2
   exit 2
fi

# Go to docker image location
cd ${DOCKER_PATH}

# Scale up / down the containers according to the specified value
echo "Scaling up / down containers..."
docker-compose -p ${DOCKER_COMPOSER_PROJECT_NAME} scale ${DOCKER_COMPOSER_CONTAINER_NAME}=$1


