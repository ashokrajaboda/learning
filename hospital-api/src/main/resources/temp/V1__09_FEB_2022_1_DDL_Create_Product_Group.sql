CREATE TABLE hospital.employee (
    name character varying NOT NULL,
    salary integer NOT NULL,
    id serial PRIMARY KEY,
    organization_id bigint
);
CREATE TABLE hospital.organization (
    name character varying NOT NULL,
    id serial PRIMARY KEY
);
