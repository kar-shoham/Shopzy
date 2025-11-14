--liquibase formatted sql

--changeset init:0
CREATE TABLE coupon_code (
                             id BIGSERIAL PRIMARY KEY,

                             created_by BIGINT NOT NULL DEFAULT 1,
                             modified_by BIGINT NOT NULL DEFAULT 1,

                             created_on TIMESTAMP NOT NULL,
                             modified_on TIMESTAMP NOT NULL,

                             code VARCHAR(255) NOT NULL,
                             value DOUBLE PRECISION NOT NULL,

                             generated_by_user_id BIGINT NOT NULL,
                             redeemed_by_user_id BIGINT,

                             redeemed BOOLEAN NOT NULL DEFAULT FALSE,

                             CONSTRAINT fk_coupon_generated_by
                                 FOREIGN KEY (generated_by_user_id)
                                     REFERENCES shopzy_user (id),

                             CONSTRAINT fk_coupon_redeemed_by
                                 FOREIGN KEY (redeemed_by_user_id)
                                     REFERENCES shopzy_user (id)
);
