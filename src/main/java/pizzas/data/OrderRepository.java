package pizzas.data;

import org.springframework.data.repository.CrudRepository;
import pizzas.PizzaOrder;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<PizzaOrder, UUID> {

}
