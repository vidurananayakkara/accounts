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

import org.wso2.examples.accounts.dataservice.beans.Account;

import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * AccountRepository class which is extended from AbstractRepository class.
 */
public final class AccountRepository extends AbstractRepository<Account> {

    /**
     * AccountRepository constructor.
     *
     * @param emf EntityManagerFactory
     */
    public AccountRepository(final EntityManagerFactory emf) {

        super(emf);
    }

    /**
     * Save the account object in database.
     *
     * @param account Account
     */
    public void createAccount(final Account account) {

        create(account);
    }

    /**
     * Remove the account object from the database.
     *
     * @param account user
     */
    public void removeAccount(final Account account) {

        remove(account);
    }

    /**
     * Find a specific account from the database.
     *
     * @param id integer
     * @return Account object
     */
    public Account findAccount(final int id) {

        return find(Account.class, id);
    }

    /**
     * Find account by searching a specific field.
     *
     * @param field      String
     * @param fieldValue Object
     * @return List<Account> account list
     */
    public List<Account> findAccountByField(String field, Object fieldValue) {

        return findByField(Account.class, field, fieldValue);
    }

    /**
     * List all accounts.
     *
     * @return List<Account>
     */
    public List<Account> findAccounts() {

        return findAll(Account.class);
    }

}
