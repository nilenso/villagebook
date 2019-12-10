#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER villagebook_test;
    CREATE DATABASE villagebook_test;
    GRANT ALL PRIVILEGES ON DATABASE villagebook_test TO villagebook_test;
EOSQL
