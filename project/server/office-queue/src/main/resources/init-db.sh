#!/bin/bash
set -e

# PostgreSQL settings
POSTGRES="psql --username postgres"

# Ensure the user exists
echo "Ensuring user 'user' exists with access..."
$POSTGRES <<-EOSQL
CREATE ROLE "user" WITH LOGIN PASSWORD 'password' CREATEDB;
EOSQL

# Databases to be created and assigned to the user
databases=(office_queue)

for db in "${databases[@]}"; do
  # Check if the database exists, and create it if it does not
  echo "Checking for database: $db"
  DB_EXIST=$($POSTGRES -tAc "SELECT 1 FROM pg_database WHERE datname='$db'")
  if [ "$DB_EXIST" = '1' ]; then
    echo "Database already exists: $db"
  else
    echo "Creating database: $db"
    $POSTGRES <<-EOSQL
CREATE DATABASE "$db" OWNER "user";
EOSQL
  fi

  # Grant privileges here (assuming the database was just created or already exists)
  echo "Granting privileges to 'user' on database: $db"
  $POSTGRES -d "$db" <<-EOSQL
GRANT ALL PRIVILEGES ON DATABASE "$db" TO "user";
EOSQL

done

echo "Database setup completed."