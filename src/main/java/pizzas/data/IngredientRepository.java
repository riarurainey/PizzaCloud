package pizzas.data;

import org.springframework.data.repository.CrudRepository;
import pizzas.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
