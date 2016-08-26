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
import org.wso2.examples.accounts.dataservice.beans.Account;
import org.wso2.examples.accounts.dataservice.beans.User;
import org.wso2.examples.accounts.dataservice.dao.AccountRepository;
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
        this.accountRepository = new AccountRepository(this.emfactory);
        this.userRepository = new UserRepository(this.emfactory);
    }

    /**
     * Clean up operations after tests are completed.
     */
    @AfterClass
    public void tearDown() {
        logger.info("Tearing down test operation...");

        // Delete user
        logger.info("Deleting user record...");

        List<User> users = this.userRepository.findUserByField(
                "email",
                testUserEmail
        );

        this.userRepository.removeUser(users.get(0));

        emfactory.close();
        logger.info("Entity manager factory closed");
        this.accountRepository = null;
    }

    /**
     * Test case for creating an account.
     */
    @Test(testName = "accountrepository")
    public void testCreateAccount() {

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
        Assert.assertEquals(testUserName, users.get(0).getName());
        Assert.assertEquals(testUserEmail, users.get(0).getEmail());
        Assert.assertEquals(testUserPassword, users.get(0).getPassword());

        // Test existence of the account in the database
        List<Account> accounts = this.accountRepository.findAccountByField(
                "user",
                users.get(0)
        );

        Assert.assertEquals(true, accounts.size() == 1);
        Assert.assertEquals(accounts.get(0).getUser().getUserId(), users.get(0).getUserId());

        logger.info("Create account test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for finding account by field.
     */
    @Test(testName = "accountrepository")
    public void testFindAccountByField() {

        logger.info("-----------------------------------------");
        logger.info("Find account by field test started...");

        List<User> users = this.userRepository.findUserByField(
                "email",
                testUserEmail
        );

        List<Account> accounts = this.accountRepository.findAccountByField(
                "user",
                users.get(0)
        );

        Assert.assertEquals(true, accounts.size() == 1);
        Assert.assertEquals(accounts.get(0).getUser().getUserId(), users.get(0).getUserId());

        logger.info("Find account by field test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for finding an account.
     */
    @Test(testName = "accountrepository")
    public void testFindAccount() {

        logger.info("-----------------------------------------");
        logger.info("Find account test started...");

        List<User> users = this.userRepository.findUserByField(
                "email",
                testUserEmail
        );

        List<Account> accounts = this.accountRepository.findAccountByField(
                "user",
                users.get(0)
        );

        // Test for find account by id
        Account account = this.accountRepository.findAccount(accounts.get(0).getAccountNumber());
        Assert.assertEquals(true, account != null);

        if (account != null) {
            Assert.assertEquals(account.getUser().getUserId(), users.get(0).getUserId());
        }

        logger.info("Find account test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for finding accounts.
     */
    @Test(testName = "accountrepository")
    public void testFindAccounts() {

        logger.info("-----------------------------------------");
        logger.info("Find all accounts test started...");

        List<Account> accounts = this.accountRepository.findAccounts();
        Assert.assertEquals(true, accounts.size() == 1);

        logger.info("Find all accounts test ended...");
        logger.info("-----------------------------------------");
    }

    /**
     * Test case for removing an account.
     */
    @Test(testName = "accountrepository")
    public void testRemoveAccount() {

        logger.info("-----------------------------------------");
        logger.info("Remove account test started...");

        List<User> users = this.userRepository.findUserByField(
                "email",
                testUserEmail
        );

        List<Account> accounts = this.accountRepository.findAccountByField(
                "user",
                users.get(0)
        );

        // Remove account
        this.accountRepository.removeAccount(accounts.get(0));

        // Assert to see if the account has been removed
        Account account = this.accountRepository.findAccount(accounts.get(0).getAccountNumber());
        Assert.assertEquals(account, null);

        logger.info("Remove account test ended...");
        logger.info("-----------------------------------------");
    }
}
