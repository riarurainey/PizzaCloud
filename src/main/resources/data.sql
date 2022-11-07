DELETE
FROM Ingredient_Ref;
DELETE
FROM Pizza;
DELETE
FROM Pizza_Order;
DELETE
FROM Ingredient;

INSERT INTO Ingredient(id, name, type)
VALUES ('CLS', 'Classic Crust', 'WRAP'),
       ('THN', 'Thin Crust', 'WRAP'),
       ('CHRZ', 'Chorizo', 'PROTEIN'),
       ('PEP', 'Pepperoni', 'PROTEIN'),
       ('TMTO', 'Diced Tomatoes', 'VEGGIES'),
       ('ON', 'Onion', 'VEGGIES'),
       ('BLG', 'Bulgarian pepper', 'VEGGIES'),
       ('OLV', 'Olive', 'VEGGIES'),
       ('CHED', 'Cheddar', 'CHEESE'),
       ('MOZ', 'Mozzarella', 'CHEESE'),
       ('PAR', 'Parmesan', 'CHEESE'),
       ('DRB', 'Danish blue', 'CHEESE'),
       ('FET', 'Feta', 'CHEESE'),
       ('RNC', 'Ranch', 'CHEESE'),
       ('TMT', 'Tomato Sauce', 'SAUCE'),
       ('MSTR', 'Mustard Sauce', 'SAUCE');
