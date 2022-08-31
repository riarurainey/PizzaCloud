package pizzas.data;

import org.springframework.data.repository.CrudRepository;
import pizzas.models.PizzaOrder;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<PizzaOrder, UUID> {

}
