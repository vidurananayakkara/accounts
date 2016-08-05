/*
*  Copyright (c) 2005-2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.msf4j.examples.accounts;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


/**
 * Accounts resource class.
 */
@Path("/accounts")
@SwaggerDefinition(
        info = @Info(
                title = "Accounts Swagger Definition", version = "1.0.0",
                description = "Sample application to demonstrate multi-tenancy in WSO2 Carbon 5",
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(
                        name = "Vidura Nanayakkara",
                        email = "vidura_nanayakkara@hotmail.com",
                        url = "http://wso2.com"
                )
        )
)
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @GET
    @Path("/{accountname}")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Returns the account corresponding to the symbol",
            notes = "Returns HTTP 404 if the account is not found")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account information is located"),
            @ApiResponse(code = 404, message = "Account information not found")})
    public String hello(@PathParam("accountname") String accountName) {
        LOGGER.info("Hello " + accountName);
        return "Hello " + accountName;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("AccountService started successfully");
    }

    @PreDestroy
    public void close() {
        LOGGER.info("AccountService shutdown completed");
    }

}

