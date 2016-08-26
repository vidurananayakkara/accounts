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

package org.wso2.examples.accounts.dataservice.unittests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.examples.accounts.dataservice.beans.User;
import org.wso2.examples.accounts.dataservice.dao.UserRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Test class for the UserRepository.
 */
public class UserRepositoryTest {

    /**
     * Logger for logging UserRepositoryTest information.
     */
    private final Logger logger = LoggerFactory.getLogger(
            UserRepositoryTest.class
    );

    /**
     * Entity manager factory name constant.
     */
    private final String entityManagerFactoryName = "org.wso2.examples.accounts.dataservice";

    /**
     * Properties file path.
     */
    private final String propertiesFilePath =
            "src/main/resources/configuration/test.configuration.properties";

    /**
     * Entity manager factory reference.
     */
    private EntityManagerFactory emfactory;

    /**
     * User repository reference.
     */
    private UserRepository userRepository;

    /**
     * Test user username.
     */
    private final String testUserName = "username";

    /**
     * Test user email.
     */
    private final String testUserEmail = "something@someplace.com";

    /**
     * Test user password.
     */
    private final String testUserPassword = "password";

    /**
     * Preparations before starting tests.
     */
    @BeforeClass
    public void setUp() {
        logger.info("Setting up tests...");

        // Loading properties
        File propertiesFile = new File(propertiesFilePath).getAbsoluteFile();

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(propertiesFile), Charset.defaultCharset())
            );
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        Properties properties = new Properties();

        try {
            properties.load(bufferedReader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        this.emfactory =
                Persistence.createEntityManagerFactory(entityManagerFactoryName, properties);
        this.userRepository = new UserRepository(this.emfactory);
    }

    /**
     * Clean up operations after tests are completed.
     */
    @AfterClass
    public void tearDown() {
        logger.info("Tearing down test operation...");
        emfactory.close();
        logger.info("Entity manager factory closed");
        this.userRepository = null;
    }

    /**
     * Test case for creating a user.
     */
    @Test(testName = "userrepository")
    public void testCreateUser() {

        logger.info("-----------------------------------------");
        logger.info("Create user test started...");

        // Create entities
        User user = new User(testUserName, testUserEmail, testUserPassword);

        // Persist entities
        this.userRepository.createUser(user);
        logger.info("Entities persisted");

        // Test existence of the user in the database
        List<User> users = this.userRepository.findUserByField(
                "email",
                user.getEmail()
        );

        Assert.assertEquals(true, users.size() == 1);
        Assert.assertEquals(testUserName, users.get(0).getName());
        Assert.assertEquals(testUserEmail, users.get(0).getEmail());
        Assert.assertEquals(testUserPassword, users.get(0).getPassword());

        logger.info("Create user test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for finding user by field.
     */
    @Test(testName = "userrepository")
    public void testFindUserByField() {

        logger.info("-----------------------------------------");
        logger.info("Find user by field test started...");

        List<User> users = this.userRepository.findUserByField(
                "email",
                testUserEmail
        );

        Assert.assertEquals(true, users.size() == 1);
        Assert.assertEquals(testUserName, users.get(0).getName());
        Assert.assertEquals(testUserEmail, users.get(0).getEmail());
        Assert.assertEquals(testUserPassword, users.get(0).getPassword());

        logger.info("Find user by field test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for finding a user.
     */
    @Test(testName = "userrepository")
    public void testFindUser() {

        logger.info("-----------------------------------------");
        logger.info("Find user test started...");

        List<User> users = this.userRepository.findUserByField(
                "email",
                testUserEmail
        );

        // Test for find user by id
        User userFound = this.userRepository.findUser(users.get(0).getUserId());
        Assert.assertEquals(true, userFound != null);

        if (userFound != null) {
            Assert.assertEquals(userFound.getUserId(), users.get(0).getUserId());
        }

        logger.info("Find user test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for finding users.
     */
    @Test(testName = "userrepository")
    public void testFindUsers() {

        logger.info("-----------------------------------------");
        logger.info("Find all users test started...");

        List<User> users = this.userRepository.findUsers();
        Assert.assertEquals(true, users.size() == 1);

        logger.info("Find all users test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for removing a user.
     */
    @Test(testName = "userrepository")
    public void testRemoveUser() {

        logger.info("-----------------------------------------");
        logger.info("Remove user test started...");

        List<User> users = this.userRepository.findUserByField(
                "email",
                testUserEmail
        );

        // Remove account
        this.userRepository.removeUser(users.get(0));

        // Assert to see if the user has been removed
        User user = this.userRepository.findUser(users.get(0).getUserId());
        Assert.assertEquals(user, null);

        logger.info("Remove user test ended...");
        logger.info("-----------------------------------------");
    }
}
