#!/usr/bin/env bash

mysql_install_db

# Start the MySQL daemon in the background.
/usr/sbin/mysqld &
mysql_pid=$!

until mysqladmin ping &>/dev/null; do
  echo -n "."; sleep 0.2
done

ok() {
    echo -e '\e[32m'$1'\e[m';
}

# Read database information from the properties file
file="./configuration.properties"

echo "Reading authentication information... "

if [ -f "$file" ]
then
    dbUser=`sed '/^\#/d' ${file} | grep 'javax.persistence.jdbc.user'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
    dbPass=`sed '/^\#/d' ${file} | grep 'javax.persistence.jdbc.password'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
    dbURL=`sed '/^\#/d' ${file} | grep 'javax.persistence.jdbc.url'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
    dbName=`tr "/" "\n" <<< ${dbURL} | tail -1`
else
    echo "Database configuration file $file not found."
fi

MYSQL=`which mysql`

echo "Creating sql user information... "

query1="GRANT ALL ON *.* TO '$dbUser'@'localhost' IDENTIFIED BY '$dbPass';"
query2="FLUSH PRIVILEGES;"
SQL_USER_GEN="${query1}${query2}"

${MYSQL} -u "$dbUser" -e "$SQL_USER_GEN"

echo "Generating sql schema and sql tables... "

Q3="DROP SCHEMA IF EXISTS $dbName;"
Q4="CREATE SCHEMA $dbName;"
Q5="USE $dbName;"
SQL_LOGIN_AND_EXECUTE="${Q3}${Q4}${Q5}"

${MYSQL} -u"$dbUser" -p"$dbPass" -e "$SQL_LOGIN_AND_EXECUTE" < "create-schema.sql"

ok "Database $dbName and user $dbUser and password $dbPass"