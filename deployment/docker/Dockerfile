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

# Pull base image
FROM ubuntu:14.04
MAINTAINER architecture@wso2.org

WORKDIR /opt

# Install Oracle Java.
RUN apt-get update                                                                         && \
    apt-get install software-properties-common -y                                          && \
    add-apt-repository ppa:webupd8team/java -y                                             && \
    apt-get update                                                                         && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections  && \
    apt-get install oracle-java8-installer -y                                              && \
    rm -rf /var/lib/apt/lists/*                                                            && \
    rm -rf /var/cache/oracle-jdk8-installer

# Install unzip zip.
RUN add-apt-repository main                                                                && \
    add-apt-repository universe                                                            && \
    add-apt-repository restricted                                                          && \
    add-apt-repository multiverse                                                          && \
    apt-get update                                                                         && \
    apt-get install zip unzip

# --------------------------------
# Environment variables
# --------------------------------
ENV WSO2_CARBON_VERSION 5.2.0-SNAPSHOT
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV CARBON_HOME /opt/wso2carbon-kernel-$WSO2_CARBON_VERSION

# --------------------------------
# Copy scripts, packages & plugins
# --------------------------------
ADD package/wso2carbon-kernel-$WSO2_CARBON_VERSION.zip /opt/
RUN unzip wso2carbon-kernel-$WSO2_CARBON_VERSION.zip
RUN rm wso2carbon-kernel-$WSO2_CARBON_VERSION.zip
COPY package/accounts.jar/ /opt/wso2carbon-kernel-$WSO2_CARBON_VERSION/osgi/dropins/

#----------------------
# Expose container ports
# ----------------------
EXPOSE 9090

#----------------------
# Start WSO2 Carbon kernel
# ----------------------
WORKDIR /opt/wso2carbon-kernel-$WSO2_CARBON_VERSION
ENTRYPOINT ./bin/carbon.sh