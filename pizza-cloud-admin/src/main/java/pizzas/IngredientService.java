package pizzas;

public interface IngredientService {
    Iterable<Ingredient> findAll();

    Ingredient addIngredient(Ingredient ingredient);
}
