package pizzas.data;

import org.springframework.data.repository.CrudRepository;

import pizzas.model.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
