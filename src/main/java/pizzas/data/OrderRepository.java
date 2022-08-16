package pizzas.data;

import pizzas.models.PizzaOrder;

public interface OrderRepository {
    PizzaOrder save(PizzaOrder order);
}
