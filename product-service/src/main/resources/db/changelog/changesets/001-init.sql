--liquibase formatted sql

--changeset init:1
CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         created_by BIGINT NOT NULL,
                         modified_by BIGINT NOT NULL,
                         created_on TIMESTAMP NOT NULL,
                         modified_on TIMESTAMP NOT NULL,
                         name VARCHAR(255),
                         code VARCHAR(255) UNIQUE,
                         quantity INT,
                         price DOUBLE PRECISION
);

CREATE TABLE review (
                        id BIGSERIAL PRIMARY KEY,
                        created_by BIGINT NOT NULL,
                        modified_by BIGINT NOT NULL,
                        created_on TIMESTAMP NOT NULL,
                        modified_on TIMESTAMP NOT NULL,
                        title VARCHAR(255),
                        description TEXT,
                        review_stars VARCHAR(50),
                        product_id BIGINT,
                        CONSTRAINT fk_review_product FOREIGN KEY (product_id)
                            REFERENCES product (id)
                            ON DELETE CASCADE
);