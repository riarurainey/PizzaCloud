package pizzas;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pizzas.Order;
import pizzas.User;
import reactor.core.publisher.Flux;

import java.awt.print.Pageable;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {

    Flux<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
