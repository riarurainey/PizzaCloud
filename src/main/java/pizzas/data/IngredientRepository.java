package pizzas.data;

import org.springframework.data.repository.CrudRepository;
import pizzas.models.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
