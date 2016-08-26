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

package org.wso2.examples.accounts.dataservice.dao;


import org.wso2.examples.accounts.dataservice.beans.User;

import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * UserRepository class which is extended from AbstractRepository class.
 */
public final class UserRepository extends AbstractRepository<User> {

    /**
     * UserRepository constructor.
     *
     * @param emf EntityManagerFactory
     */
    public UserRepository(final EntityManagerFactory emf) {

        super(emf);
    }

    /**
     * Save the user object in database.
     *
     * @param user user
     */
    public void createUser(final User user) {

        create(user);
    }

    /**
     * Remove the user object from the database.
     *
     * @param user user
     */
    public void removeUser(final User user) {

        remove(user);
    }

    /**
     * Find a specific user from the database.
     *
     * @param id integer
     * @return User object
     */
    public User findUser(final int id) {

        return find(User.class, id);
    }

    /**
     * Find user by searching a specific field.
     *
     * @param field      String
     * @param fieldValue Object
     * @return List<User> user list
     */
    public List<User> findUserByField(String field, Object fieldValue) {

        return findByField(User.class, field, fieldValue);
    }

    /**
     * List all users.
     *
     * @return List<User>
     */
    public List<User> findUsers() {

        return findAll(User.class);
    }
}
