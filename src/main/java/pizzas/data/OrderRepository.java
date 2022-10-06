package pizzas.data;

import org.springframework.data.repository.CrudRepository;
import pizzas.model.PizzaOrder;

public interface OrderRepository extends CrudRepository<PizzaOrder, String> {

}
