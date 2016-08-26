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

package org.wso2.examples.accounts.dataservice.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Run sql script.
 */
public final class JpaSchemaRun {

    /**
     * Logger for logging AccountRepositoryTest information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            JpaSchemaRun.class
    );

    /**
     * SQL script delimiter constant.
     */
    private static final String DELIMITER = "\n\n";

    /**
     * Properties string jdbc driver name.
     */
    private static final String PROPERTIES_JDBC_DRIVER =
            "javax.persistence.jdbc.driver";

    /**
     * Properties string jdbc url.
     */
    private static final String PROPERTIES_JDBC_URL =
            "javax.persistence.jdbc.url";

    /**
     * Properties string jdbc user.
     */
    private static final String PROPERTIES_JDBC_USER =
            "javax.persistence.jdbc.user";

    /**
     * Properties string jdbc password.
     */
    private static final String PROPERTIES_JDBC_PASSWORD =
            "javax.persistence.jdbc.password";

    /**
     * Private constructor.
     */
    private JpaSchemaRun() {

    }

    /**
     * Start point for launching database scripts.
     *
     * @param args String
     * @throws ClassNotFoundException class not found exception
     * @throws SQLException           sql exception
     * @throws IOException            IO exception
     */
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException, IOException {

        File propertiesFile = new File(args[0]).getAbsoluteFile();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(propertiesFile),
                        Charset.defaultCharset()
                )
        );
        Properties properties = new Properties();
        properties.load(bufferedReader);

        // Get credentials from properties file
        String datasourceDriver =
                properties.getProperty(PROPERTIES_JDBC_DRIVER);
        String datasourceUrl =
                properties.getProperty(PROPERTIES_JDBC_URL);
        String datasourceUsername =
                properties.getProperty(PROPERTIES_JDBC_USER);
        String datasourcePassword =
                properties.getProperty(PROPERTIES_JDBC_PASSWORD);

        // Create MySql Connection
        Class.forName(datasourceDriver);
        Connection connection = DriverManager.getConnection(
                datasourceUrl, datasourceUsername, datasourcePassword);
        File file = new File(args[1]).getAbsoluteFile();

        executeSqlScript(connection, file);
    }

    /**
     * Execute sql script.
     *
     * @param conn      Connection
     * @param inputFile File
     * @throws FileNotFoundException File not found exception
     * @throws SQLException          SQL exception
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING"
    )
    private static void executeSqlScript(
            final Connection conn, final File inputFile)
            throws FileNotFoundException, SQLException {

        Scanner scanner = new Scanner(
                inputFile,
                Charset.defaultCharset().toString()
        ).useDelimiter(DELIMITER);

        // Loop through the SQL file statements
        while (scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next().concat(DELIMITER);

            // Execute statement
            try (PreparedStatement preparedStatement =
                         conn.prepareStatement(rawStatement)) {
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }

        scanner.close();
    }
}
