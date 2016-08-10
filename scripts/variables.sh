#!/usr/bin/env bash

# Paths
HOME_PATH=$(cd ../; pwd)
DOCKER_PATH=${HOME_PATH}/deployment/docker

# Variables
PRE_REQ=1
VERSION=1.0-SNAPSHOT
DOCKER_IMAGE_NAME=c5/accountsservice:${VERSION}
WSO2_CARBON_VERSION=5.2.0-SNAPSHOT