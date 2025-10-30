--liquibase formatted sql

--changeset seed:0
INSERT INTO product (created_by, modified_by, created_on, modified_on, name, code, quantity, price)
VALUES
    (1, 1, NOW(), NOW(), 'iPhone 15 Pro', 'P-IPH15', 50, 134999.00),
    (1, 1, NOW(), NOW(), 'Samsung Galaxy S24', 'P-SAM24', 80, 99999.00),
    (1, 1, NOW(), NOW(), 'OnePlus 12', 'P-ONE12', 60, 69999.00);

INSERT INTO review (created_by, modified_by, created_on, modified_on, title, description, review_stars, product_id)
VALUES
    (2, 2, NOW(), NOW(), 'Amazing camera', 'The iPhone 15 Pro camera is insane. Totally worth it.', 'FIVE', 1),
    (3, 3, NOW(), NOW(), 'Battery could be better', 'The phone is fast but drains quickly.', 'THREE', 1),
    (4, 4, NOW(), NOW(), 'Flagship performance', 'Galaxy S24 delivers premium experience.', 'FOUR', 2),
    (5, 5, NOW(), NOW(), 'Value for money', 'OnePlus 12 has great performance for the price.', 'FIVE', 3);
