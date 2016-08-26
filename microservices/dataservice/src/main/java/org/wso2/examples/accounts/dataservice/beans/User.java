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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents an account user.
 */
@Entity
@Table
public final class User implements Serializable {

    /**
     * Serializable UID
     */
    private static final long serialVersionUID = 21L;

    /**
     * Primary key to identify a user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    /**
     * Email address of a user.
     */
    @Column(unique = true, nullable = false, length = 254)
    private String email;

    /**
     * Password of a user.
     */
    @Column(length = 50)
    private String password;

    /**
     * Name of the user.
     */
    @Column(nullable = false, length = 128)
    private String name;

    /**
     * User default constructor.
     */
    public User() {

    }

    /**
     * User constructor.
     *
     * @param username     String
     * @param emailaddress String
     * @param pass         String
     */
    public User(final String username,
                final String emailaddress,
                final String pass) {
        setName(username);
        setEmail(emailaddress);
        setPassword(pass);
    }

    /**
     * Get the user id.
     *
     * @return int user id
     */
    public int getUserId() {
        return userid;
    }

    /**
     * Get the email address of the user.
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email address of a user.
     *
     * @param emailaddress String
     */
    public void setEmail(final String emailaddress) {
        this.email = emailaddress;
    }

    /**
     * Get the password of the user.
     *
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of a user.
     *
     * @param pass String
     */
    public void setPassword(final String pass) {
        this.password = pass;
    }

    /**
     * Get the name of a user.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of a user.
     *
     * @param username String
     */
    public void setName(final String username) {
        this.name = username;
    }

    /**
     * To String method.
     *
     * @return name String
     */
    @Override
    public String toString() {
        return getName();
    }
}
