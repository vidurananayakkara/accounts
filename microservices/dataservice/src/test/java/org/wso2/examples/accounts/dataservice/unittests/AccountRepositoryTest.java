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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.examples.accounts.dataservice.beans.Account;
import org.wso2.examples.accounts.dataservice.beans.User;
import org.wso2.examples.accounts.dataservice.dao.AccountRepository;
import org.wso2.examples.accounts.dataservice.dao.UserRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

/**
 * Test class for the AccountRepository.
 */
public class AccountRepositoryTest {

    /**
     * Logger for logging AccountRepositoryTest information.
     */
    private final Logger logger = LoggerFactory.getLogger(
            AccountRepositoryTest.class
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
     * Account repository reference.
     */
    private AccountRepository accountRepository;

    /**
     * User repository reference.
     */
    private UserRepository userRepository;

    /**
     * Preparations before starting tests.
     */
    @BeforeMethod
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
        this.accountRepository = new AccountRepository(this.emfactory);
        this.userRepository = new UserRepository(this.emfactory);
    }

    /**
     * Clean up operations after tests are completed.
     */
    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test operation...");
        emfactory.close();
        logger.info("Entity manager factory closed");
        this.accountRepository = null;
    }

    /**
     * Test case for creating an account.
     */
    @Test(groups = "repository")
    public void testCreateAccount() {

        String testUserName = "username";
        String testUserEmail = "something@someplace.com";
        String testUserPassword = "password";

        logger.info("-----------------------------------------");
        logger.info("Create account test started...");

        // Create entities
        User user = new User(testUserName, testUserEmail, testUserPassword);
        Account account = new Account(user);

        // Persist entities
        this.userRepository.createUser(user);
        this.accountRepository.createAccount(account);
        logger.info("Entities persisted");

        // Test existence of the user in the database
        List<User> users = this.userRepository.findUserByField(
                "email",
                user.getEmail()
        );
        Assert.assertEquals(true, users.size() == 1);
        logger.info("User validation completed");

        // Test existence of the account in the database
        List<Account> accounts = this.accountRepository.findAccountByField(
                "user",
                users.get(0)
        );
        Assert.assertEquals(true, accounts.size() == 1);
        logger.info("Account validation completed");

        logger.info("Create account test ended...");
        logger.info("-----------------------------------------");
    }

}
