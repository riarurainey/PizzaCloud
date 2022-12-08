package pizzas;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pizzas.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, Long> {

    Mono<Ingredient> findBySlug(String slug);

}
