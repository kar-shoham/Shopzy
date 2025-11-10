--liquibase formatted sql

--changeset seed:0
INSERT INTO shopzy_user
(created_by, modified_by, created_on, modified_on,
 username, full_name, password, user_type, wallet_amt)
VALUES
    (1,1,NOW(),NOW(),
     'admin@shopzy.com', 'Admin User',
     '$2a$10$YV5S3m8I7j0hP8G67qQ92.ah9t7dTp5/ZmQIi4nivNbNzNaM1m9Va',
     'ADMIN', 9999.99);
