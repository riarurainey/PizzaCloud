package pizzas.data;

import org.springframework.data.repository.CrudRepository;
import pizzas.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
