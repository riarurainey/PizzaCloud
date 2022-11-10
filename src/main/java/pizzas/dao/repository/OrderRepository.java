package pizzas.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pizzas.dao.model.PizzaOrder;
import pizzas.dao.model.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {

    List<PizzaOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
