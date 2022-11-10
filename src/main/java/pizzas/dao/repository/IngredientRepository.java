package pizzas.dao.repository;

import org.springframework.data.repository.CrudRepository;
import pizzas.dao.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
