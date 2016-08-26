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

# Move to sample's home directory
cd ${HOME_PATH}

# ------------------------------------------------------------------------
# Check for prerequisites
# ------------------------------------------------------------------------
PRE_REQ=1

# Check if docker exists
command -v docker >/dev/null 2>&1 || { echo >&2 "Missing docker!!! Build required docker install in the host.";${PRE_REQ}=1; }

# Check if docker-compose exists
command -v docker-compose >/dev/null 2>&1 || { echo >&2 "Missing docker-compose!!! Build required docker-compose install in the host.";${PRE_REQ}=1; }

if [ ${PRE_REQ} -eq 0 ];then
    echo "--------------------------------------------------------------"
    echo "Prerequisite not met. Existing build..."
    echo "--------------------------------------------------------------"
    exit;
fi

echo "Copying WSO2 Carbon Kernel $WSO2_CARBON_VERSION to packages location..."
cp ${HOME_PATH}/packs/wso2carbon-kernel-${WSO2_CARBON_VERSION}.zip ${HOME_PATH}/deployment/docker/package/

${SCRIPT_PATH}/bootstrap.sh
