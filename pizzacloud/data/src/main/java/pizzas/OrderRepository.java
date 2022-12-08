package pizzas;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pizzas.Order;
import pizzas.User;
import reactor.core.publisher.Flux;

import java.awt.print.Pageable;
import java.util.UUID;

public interface OrderRepository extends ReactiveCrudRepository<Order, UUID> {

    Flux<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
