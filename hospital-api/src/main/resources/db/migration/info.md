SQL-based migrations are typically used for

DDL changes (CREATE/ALTER/DROP statements for TABLES,VIEWS,TRIGGERS,SEQUENCES,…)
Simple reference data changes (CRUD in reference data tables)
Simple bulk data changes (CRUD in regular data tables)
Naming
In order to be picked up by Flyway, SQL migrations must comply with the following naming pattern:

Versioned Migrations
Prefix  Separator       Suffix

V2__Add_new_table.sql

Version    Description
Undo Migrations
Prefix  Separator       Suffix

U2__Add_new_table.sql

Version    Description
Repeatable Migrations
Prefix Separator       Suffix

    R__Add_new_table.sql
               
           Description
The file name consists of the following parts:

Prefix: V for versioned (configurable), U for undo (configurable) and R for repeatable migrations (configurable)
Version: Version with dots or underscores separate as many parts as you like (Not for repeatable migrations)
Separator: __ (two underscores) (configurable)
Description: Underscores or spaces separate the words
Suffix: .sql (configurable)