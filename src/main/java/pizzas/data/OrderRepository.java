package pizzas.data;

import org.springframework.data.repository.CrudRepository;
import pizzas.models.PizzaOrder;

public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {

}
