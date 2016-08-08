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

"$SCRIPT_PATH/bootstrap.sh"

