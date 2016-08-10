#!/usr/bin/env bash

# Change directory to the scripts directory
pushd $(dirname $0) > /dev/null
SCRIPT_PATH=$(pwd -P)
popd > /dev/null
cd ${SCRIPT_PATH}

# Exporting variables
source variables.sh

# Move to sample's home directory
cd ${HOME_PATH}

# Check if docker exists
command -v docker >/dev/null 2>&1 || { echo >&2 "Missing Docker!!! Build required docker install in the host. Try 'curl -sSL https://get.docker.com/ | sh' ";${PRE_REQ}=1; }

if [ ${PRE_REQ} -eq 0 ];then
    echo "--------------------------------------------------------------"
    echo "Prerequisite not met. Existing build..."
    echo "--------------------------------------------------------------"
    exit;
fi

# Extract WSO2 carbon kernel
# This version of the WSO2 carbon kernel should include the WSO2 MSF4J feature included
# /packs contains a WSO2 carbon kernel which has the MSF4J feature included
# Refer /packs/README.md for including MSF4J feature in the WSO2 carbon kernel
#unzip ${HOME_PATH}/packs/wso2carbon-kernel-${WSO2_CARBON_VERSION}.zip -d ${HOME_PATH}/deployment/docker/package

cp ${HOME_PATH}/packs/wso2carbon-kernel-5.2.0-SNAPSHOT.zip ${HOME_PATH}/deployment/docker/package/

${SCRIPT_PATH}/bootstrap.sh
