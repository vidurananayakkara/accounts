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
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.examples.accounts.dataservice.beans.User;
import org.wso2.examples.accounts.dataservice.dao.UserRepository;
import org.wso2.msf4j.Microservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        name = "UserResource",
        service = Microservice.class,
        immediate = true
)
@Path("/users")
@SwaggerDefinition(
        info = @Info(
                title = "User Resource Swagger Definition",
                version = "1.0.0",
                description = "Accesses users in the database",
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
public class UserResource implements Microservice {

    /**
     * Logger for logging UserResource information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            UserResource.class
    );

    /**
     * User repository reference.
     */
    private UserRepository users;

    /**
     * Operations to perform when the bundle is activated.
     *
     * @param bundleContext bundle context
     */
    @Activate
    protected void activate(BundleContext bundleContext) {
        LOGGER.info("User resource bundle activated successfully");
    }

    /**
     * Operations to perform when the bundle is activated.
     *
     * @param bundleContext bundle context
     */
    @Deactivate
    protected void deactivate(BundleContext bundleContext) {
        LOGGER.info("User resource bundle deactivated successfully");
    }

    /**
     * UserResource constructor.
     */
    public UserResource() {

        String entityManagerFactoryName = "org.wso2.examples.accounts.dataservice";
        String fileName =
                "configuration/configuration.properties";

        BundleContext context = FrameworkUtil.getBundle(AccountResource.class).getBundleContext();
        URL configURL = context.getBundle().getEntry(fileName);
        Properties properties = new Properties();

        if (configURL != null) {
            try {
                InputStream input = configURL.openStream();
                properties.load(input);
                input.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }

        EntityManagerFactory emfactory =
                Persistence.createEntityManagerFactory(entityManagerFactoryName, properties);

        this.users = new UserRepository(emfactory);
    }

    /**
     * Add a user to the database.
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
            value = "Adds a user to the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "User added successfully"
            )
    })
    public Response addUser(@PathParam("username") String username,
                            @PathParam("emailaddress") String emailaddress,
                            @PathParam("pass") String pass) {

        User user = new User(username, emailaddress, pass);
        users.createUser(user);

        LOGGER.info("User added to the database successfully");

        return Response.accepted().build();
    }

    /**
     * Get a user from the database.
     *
     * @param id integer
     * @return Response user object
     */
    @GET
    @Path("/{id}")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Gets a user from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "User found"
            ),
            @ApiResponse(
                    code = 404,
                    message = "User not found"
            )
    })
    public Response getUser(@PathParam("id") int id) {

        User user = users.findUser(id);

        if (user != null) {

            LOGGER.info(
                    new StringBuilder()
                            .append("User ")
                            .append(id)
                            .append(" is located")
                            .toString()
            );

            return Response.status(Response.Status.ACCEPTED)
                    .entity(user).build();
        }

        LOGGER.info(
                new StringBuilder()
                        .append("User ")
                        .append(id)
                        .append(" is not located")
                        .toString()
        );

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Deletes a user from the database.
     *
     * @param id integer
     * @return Response user object
     */
    @DELETE
    @Path("/{id}")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Deletes a user from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "User removed from the database"
            ),
            @ApiResponse(
                    code = 404,
                    message = "User not found"
            )
    })
    public Response deleteUser(@PathParam("id") int id) {

        User user = users.findUser(id);

        if (user != null) {

            users.removeUser(user);

            LOGGER.info(
                    new StringBuilder()
                            .append("User ")
                            .append(id)
                            .append(" is deleted")
                            .toString()
            );

            return Response.status(Response.Status.ACCEPTED)
                    .entity(user).build();
        }

        LOGGER.info(
                new StringBuilder()
                        .append("User ")
                        .append(id)
                        .append(" is not located")
                        .toString()
        );

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Gets all users from the database.
     *
     * @return Response user object
     */
    @GET
    @Path("/")
    @Produces({"application/json", "text/xml"})
    @ApiOperation(
            value = "Gets all users from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 202,
                    message = "User removed from the database"
            )
    })
    public Response getUsers() {

        LOGGER.info("Getting all users...");

        return Response.status(Response.Status.ACCEPTED)
                .entity(users.findUsers()).build();
    }

}
