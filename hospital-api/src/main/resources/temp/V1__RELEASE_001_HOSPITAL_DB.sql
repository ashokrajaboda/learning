-- Role: gaganspvt
-- DROP ROLE IF EXISTS gaganspvt;

CREATE ROLE gaganspvt WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION;

 -- Role: hospital_admin
 -- DROP ROLE IF EXISTS hospital_admin;

 CREATE ROLE hospital_admin WITH
   LOGIN
   NOSUPERUSER
   INHERIT
   NOCREATEDB
   NOCREATEROLE
   NOREPLICATION
   ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:ClVEuaYQD0u5uyR4hjGPYA==$Oj1WxjklDsXUCGuVEAFfJ2FY7A6qbUyo5vZTk/QzCX8=:M/deRDkr6vuQ3MB+tSI95U2fyjbAkeQSW6V8x1/18kE=';

   -- Role: admin
   -- DROP ROLE IF EXISTS admin;

   CREATE ROLE admin WITH
     LOGIN
     NOSUPERUSER
     INHERIT
     CREATEDB
     CREATEROLE
     NOREPLICATION
     ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:1IVmADuh9oPsAUPyLQf5bA==$zgmHm7UA8OzkVdi629nPJ6QbD7Z4siJ37OzfIzqwxtg=:VQ8eQF7o/mk3r0o6oWlFwi72GctfF1e4CbVc6+lOsKM=';

   GRANT gaganspvt, hospital_admin TO admin;

   -- Database: hospital_db

   -- DROP DATABASE IF EXISTS hospital_db;

   CREATE DATABASE hospital_db
       WITH
       OWNER = admin
       ENCODING = 'UTF8'
       LC_COLLATE = 'C'
       LC_CTYPE = 'C'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1
       IS_TEMPLATE = False;

  -- SCHEMA: migrate_flyway

  -- DROP SCHEMA IF EXISTS migrate_flyway ;

  CREATE SCHEMA IF NOT EXISTS migrate_flyway
      AUTHORIZATION admin;