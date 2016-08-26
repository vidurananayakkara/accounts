///*
//*  Copyright (c) 2005-2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//*
//*  WSO2 Inc. licenses this file to you under the Apache License,
//*  Version 2.0 (the "License"); you may not use this file except
//*  in compliance with the License.
//*  You may obtain a copy of the License at
//*
//*    http://www.apache.org/licenses/LICENSE-2.0
//*
//* Unless required by applicable law or agreed to in writing,
//* software distributed under the License is distributed on an
//* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
//* KIND, either express or implied.  See the License for the
//* specific language governing permissions and limitations
//* under the License.
//*/
//
//package org.wso2.examples.accounts.dataservice.unittests;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.wso2.examples.accounts.dataservice.dao.UserRepository;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
///**
// * Test class for the UserRepository.
// */
//public class UserRepositoryTest {
//
//    /**
//     * Logger for logging UserRepositoryTest information.
//     */
//    private final Logger logger = LoggerFactory.getLogger(
//            UserRepositoryTest.class
//    );
//
//    /**
//     * Entity manager factory name constant.
//     */
//    private final String entityManagerFactoryName = "org.wso2.examples.accounts.dataservice";
//
//    /**
//     * Entity manager factory reference.
//     */
//    private EntityManagerFactory emfactory;
//
//    /**
//     * User repository reference.
//     */
//    private UserRepository userRepository;
//
//    /**
//     * Preparations before starting tests.
//     */
//    @BeforeMethod
//    public void setUp() {
//        logger.info("Setting up tests...");
//        this.emfactory = Persistence.createEntityManagerFactory(entityManagerFactoryName);
//        this.userRepository = new UserRepository(this.emfactory);
//    }
//
//    /**
//     * Clean up operations after tests are completed.
//     */
//    @AfterMethod
//    public void tearDown() {
//        logger.info("Tearing down test operation...");
//        emfactory.close();
//        logger.info("Entity manager factory closed");
//        this.userRepository = null;
//    }
//}
