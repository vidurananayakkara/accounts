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

KUBERNETES_VERSION=1.3.6
IS_KUBECTL=1

echo "--------------------------------------------------------------"
echo "Copying micro-service account service files..."
echo "--------------------------------------------------------------"
mkdir -p ${HOME_PATH}/vagrant/microservices/accountservice/deployment/package/
cp ${HOME_PATH}/packs/wso2carbon-kernel-${WSO2_CARBON_VERSION}.zip ${HOME_PATH}/vagrant/microservices/accountservice/deployment/package/
cp ${HOME_PATH}/microservices/accountservice/deployment/Dockerfile ${HOME_PATH}/vagrant/microservices/accountservice/deployment/

echo "--------------------------------------------------------------"
echo "Copying micro-service data service files..."
echo "--------------------------------------------------------------"
mkdir -p ${HOME_PATH}/vagrant/microservices/dataservice/deployment/package/
cp ${HOME_PATH}/packs/wso2carbon-kernel-${WSO2_CARBON_VERSION}.zip ${HOME_PATH}/vagrant/microservices/dataservice/deployment/package/
cp ${HOME_PATH}/microservices/dataservice/src/main/resources/configuration/configuration.properties ${HOME_PATH}/vagrant/microservices/dataservice/deployment/package/
cp ${HOME_PATH}/microservices/dataservice/target/hibernate3/sql/create-schema.sql ${HOME_PATH}/vagrant/microservices/dataservice/deployment/package/
cp ${HOME_PATH}/microservices/dataservice/deployment/Dockerfile ${HOME_PATH}/vagrant/microservices/accountservice/deployment/

echo "--------------------------------------------------------------"
echo "Setting up CoreOS and Kubernetes"
echo "--------------------------------------------------------------"

# Check if kubectl exists
command -v kubectl >/dev/null 2>&1 || { echo >&2 "kubectl is missing... proceeding download... ";${IS_KUBECTL}=1; }

if [ ${IS_KUBECTL} -eq 0 ];then
    echo "--------------------------------------------------------------"
    echo "Downloading kubectl..."
    echo "--------------------------------------------------------------"
    if [[ "$OSTYPE" == "linux-gnu" ]]; then
        wget https://storage.googleapis.com/kubernetes-release/release/v${KUBERNETES_VERSION}/bin/linux/amd64/kubectl
    elif [[ "$OSTYPE" == "darwin"* ]]; then
       wget https://storage.googleapis.com/kubernetes-release/release/v${KUBERNETES_VERSION}/bin/darwin/amd64/kubectl
    fi
    chmod +x kubectl
    mv kubectl /usr/local/bin/kubectl
fi

KUBECTL=`which kubectl`

cd ${VAGRANT_HOME}
vagrant up