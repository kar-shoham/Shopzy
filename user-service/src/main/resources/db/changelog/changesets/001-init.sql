--liquibase formatted sql

--changeset init:0
CREATE TABLE shopzy_user (
                             id BIGSERIAL PRIMARY KEY,
                             created_by BIGINT NOT NULL DEFAULT 1,
                             modified_by BIGINT NOT NULL DEFAULT 1,
                             created_on TIMESTAMP NOT NULL,
                             modified_on TIMESTAMP NOT NULL,
                             username VARCHAR(255) NOT NULL UNIQUE,
                             full_name VARCHAR(255) NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             user_type VARCHAR(50) NOT NULL,
                             wallet_amt DOUBLE PRECISION
);
