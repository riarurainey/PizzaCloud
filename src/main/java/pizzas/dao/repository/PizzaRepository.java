package pizzas.dao.repository;

import org.springframework.data.repository.CrudRepository;
import pizzas.dao.model.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
