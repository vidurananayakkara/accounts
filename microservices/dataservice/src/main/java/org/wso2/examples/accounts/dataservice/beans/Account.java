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

package org.wso2.examples.accounts.dataservice.beans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Holds the account information of an account.
 */
@Entity
@Table(name = "Account")
public final class Account implements Serializable {

    /**
     * Serializable UID
     */
    private static final long serialVersionUID = 42L;

    /**
     * Account number of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountnumber;

    /**
     * Holds the user reference for the account.
     */
    @OneToOne(optional = false)
    private User user;

    /**
     * Account default constructor.
     */
    public Account() {

    }

    /**
     * Account constructor.
     *
     * @param user User
     */
    public Account(final User user) {
        setUser(user);
    }

    /**
     * Get the account number of the account.
     *
     * @return integer account number
     */
    public int getAccountNumber() {
        return accountnumber;
    }

    /**
     * Get the user information for the account.
     *
     * @return User account user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user information of the account.
     *
     * @param userobject User
     */
    public void setUser(final User userobject) {
        this.user = userobject;
    }

    /**
     * To String method
     *
     * @return account number String
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append(Integer.toString(getAccountNumber()))
                .append(getUser())
                .toString();
    }
}
