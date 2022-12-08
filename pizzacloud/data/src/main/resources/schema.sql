DROP TABLE IF EXISTS Ingredient;
DROP TABLE IF EXISTS Pizza;
DROP TABLE IF EXISTS Pizza_Order;

CREATE TABLE Ingredient
(
    id IDENTITY,
    slug VARCHAR(4)  NOT NULL,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL
);


CREATE TABLE Pizza
(
    id IDENTITY,
    name           VARCHAR(50) NOT NULL,
    ingredient_ids array
);

CREATE TABLE Pizza_Order
(
    id IDENTITY,
    delivery_name   VARCHAR(50) NOT NULL,
    delivery_street VARCHAR(50) NOT NULL,
    delivery_city   VARCHAR(50) NOT NULL,
    delivery_state  VARCHAR(2)  NOT NULL,
    delivery_zip    VARCHAR(10) NOT NULL,
    cc_number       VARCHAR(16) NOT NULL,
    cc_expiration   VARCHAR(5)  NOT NULL,
    cc_cvv          VARCHAR(3)  NOT NULL,
    pizza_ids       array
);