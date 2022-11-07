package pizzas.data;

import pizzas.Ingredient;

import java.util.Optional;


public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
