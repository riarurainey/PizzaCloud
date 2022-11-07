package pizzas.data;

import pizzas.PizzaOrder;

import java.util.Optional;

public interface OrderRepository {
    PizzaOrder save(PizzaOrder pizzaOrder);

    Optional<PizzaOrder> findById(Long id);
}
