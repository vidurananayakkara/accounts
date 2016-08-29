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

# Paths
HOME_PATH=$(cd ../; pwd)
DOCKER_PATH_ACCOUNT_SERVICE=${HOME_PATH}/microservices/accountservice/deployment
DOCKER_PATH_DATA_SERVICE=${HOME_PATH}/microservices/dataservice/deployment

# Variables
VERSION=1.0-SNAPSHOT
DOCKER_IMAGE_NAME_ACCOUNT_SERVICE=c5/accountsservice:${VERSION}
DOCKER_IMAGE_NAME_DATA_SERVICE=c5/dataservice:${VERSION}
WSO2_CARBON_VERSION=5.2.0-SNAPSHOT