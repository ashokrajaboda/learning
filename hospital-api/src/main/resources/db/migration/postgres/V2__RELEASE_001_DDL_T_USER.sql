CREATE TABLE hospital.t_user (
    user_id SERIAL PRIMARY KEY,
    username varchar(64) NOT NULL,
    password varchar(512),
    roles TEXT[],
    version integer,
    enabled BOOLEAN,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

--GRANT INSERT, SELECT, UPDATE, DELETE, TRIGGER ON TABLE hospital.t_user TO hospital_admin;

--GRANT SELECT ON SEQUENCE hospital.t_user_user_id_seq TO hospital_admin;

