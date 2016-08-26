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

package org.wso2.examples.accounts.accountservice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.examples.accounts.accountservice.exceptions.AccountException;
import org.wso2.msf4j.Microservice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * AccountService micro-service.
 */
@Component(
        name = "AccountService",
        service = Microservice.class,
        immediate = true
)
@Path("/accounts")
@SwaggerDefinition(
        info = @Info(
                title = "Accounts Swagger Definition",
                version = "1.0.0",
                description = "Demonstrate multi-tenancy in WSO2 Carbon 5",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(
                        name = "Vidura Nanayakkara",
                        email = "vidura_nanayakkara@hotmail.com",
                        url = "http://wso2.com"
                )
        )
)
public class AccountService implements Microservice {

    /**
     * Logger for logging AccountService information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            AccountService.class
    );

    /**
     * Operations to perform when the bundle is activated.
     *
     * @param bundleContext bundle context
     */
    @Activate
    protected void activate(BundleContext bundleContext) {
        LOGGER.info("AccountService bundle activated successfully");
    }

    /**
     * Operations to perform when the bundle is activated.
     *
     * @param bundleContext bundle context
     */
    @Deactivate
    protected void deactivate(BundleContext bundleContext) {
        LOGGER.info("AccountService bundle deactivated successfully");
    }

    /**
     * Retrieve account information for a given account.
     * http://localhost:{carbon configuration port}/accounts/{SomeAccountName}
     *
     * @param accountname account name
     * @return account name
     * @throws AccountException account exception
     */
    @GET
    @Path("/{accountname}")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Returns the account corresponding to the symbol",
            notes = "Returns HTTP 404 if the account is not found")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Account information is located"
            ),
            @ApiResponse(
                    code = 404,
                    message = "Account information not found"
            )
    })
    public Response hello(@PathParam("accountname") String accountname)
            throws AccountException {

        if (accountname == null || accountname.isEmpty()) {
            LOGGER.info("Account not found");
            throw new AccountException("Account not found");
        }

        LOGGER.info("Account " + accountname + " located");
        return Response.status(Response.Status.OK).build();
    }

    /**
     * Operations to perform when starting micro-service.
     */
    @PostConstruct
    public void init() {

        LOGGER.info("AccountService started successfully");
    }

    /**
     * Operations to perform when exiting micro-service.
     */
    @PreDestroy
    public void close() {

        LOGGER.info("AccountService shutdown completed");
    }

}
