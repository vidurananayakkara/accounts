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

package org.wso2.examples.accounts.dataservice.resources;

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
import org.wso2.examples.accounts.dataservice.beans.Account;
import org.wso2.examples.accounts.dataservice.beans.User;
import org.wso2.examples.accounts.dataservice.dao.AccountRepository;
import org.wso2.msf4j.Microservice;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Annotated Resource class.
 */
@Component(
        name = "AccountResource",
        service = Microservice.class,
        immediate = true
)
@Path("/accounts")
@SwaggerDefinition(
        info = @Info(
                title = "Account Resource Swagger Definition",
                version = "1.0.0",
                description = "Accesses accounts in the database",
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
public class AccountResource implements Microservice {

    /**
     * Logger for logging AccountResource information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            AccountResource.class
    );

    /**
     * User repository reference.
     */
    private AccountRepository accounts;

    /**
     * Operations to perform when the bundle is activated.
     *
     * @param bundleContext bundle context
     */
    @Activate
    protected void activate(BundleContext bundleContext) {
        LOGGER.info("Account resource bundle activated successfully");
    }

    /**
     * Operations to perform when the bundle is activated.
     *
     * @param bundleContext bundle context
     */
    @Deactivate
    protected void deactivate(BundleContext bundleContext) {
        LOGGER.info("Account resource bundle deactivated successfully");
    }

    /**
     * AccountResource constructor.
     *
     * @param accountRepository AccountRepository
     */
    public AccountResource(final AccountRepository accountRepository) {

        this.accounts = accountRepository;
    }

    /**
     * Add an account to the database.
     *
     * @param username     String
     * @param emailaddress String
     * @param pass         String
     * @return Response
     */
    @POST
    @Path("/username/{username}/emailaddress/{emailaddress}/pass/{pass}")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Adds an account to the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "Account added successfully"
            )
    })
    public Response addAccount(@PathParam("username") String username,
                               @PathParam("emailaddress") String emailaddress,
                               @PathParam("pass") String pass) {

        User user = new User(username, emailaddress, pass);

        Account account = new Account(user);
        accounts.createAccount(account);

        LOGGER.info("Account added to the database successfully");

        return Response.accepted().build();
    }

    /**
     * Get an account from the database.
     *
     * @param id integer
     * @return Response account object
     */
    @GET
    @Path("/{id}")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Gets an account from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "Account found"
            ),
            @ApiResponse(
                    code = 404,
                    message = "Account not found"
            )
    })
    public Response getAccount(@PathParam("id") int id) {

        Account account = accounts.findAccount(id);

        if (account != null) {

            LOGGER.info(
                    new StringBuilder()
                            .append("Account ")
                            .append(id)
                            .append(" is located")
                            .toString()
            );

            return Response.status(Response.Status.ACCEPTED)
                    .entity(account).build();
        }

        LOGGER.info(
                new StringBuilder()
                        .append("Account ")
                        .append(id)
                        .append(" is not located")
                        .toString()
        );

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Deletes an account from the database.
     *
     * @param id integer
     * @return Response account object
     */
    @DELETE
    @Path("/{id}")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Deletes an account from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "Account removed from the database"
            ),
            @ApiResponse(
                    code = 404,
                    message = "Account not found"
            )
    })
    public Response deleteAccount(@PathParam("id") int id) {

        Account account = accounts.findAccount(id);

        if (account != null) {

            accounts.removeAccount(account);

            LOGGER.info(
                    new StringBuilder()
                            .append("Account ")
                            .append(id)
                            .append(" is deleted")
                            .toString()
            );

            return Response.status(Response.Status.ACCEPTED)
                    .entity(account).build();
        }

        LOGGER.info(
                new StringBuilder()
                        .append("Account ")
                        .append(id)
                        .append(" is not located")
                        .toString()
        );

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Gets all the accounts from the database.
     *
     * @return Response accounts list
     */
    @GET
    @Path("/")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Gets all the accounts from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "List of accounts found in the database"
            )
    })
    public Response getAccounts() {

        LOGGER.info("Getting all accounts...");

        return Response.status(Response.Status.ACCEPTED)
                .entity(accounts.findAccounts()).build();
    }
}
