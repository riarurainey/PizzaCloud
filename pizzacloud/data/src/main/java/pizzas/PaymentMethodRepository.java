package pizzas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pizzas.PaymentMethod;
import reactor.core.publisher.Mono;

public interface PaymentMethodRepository extends ReactiveCrudRepository<PaymentMethod, String> {
    Mono<PaymentMethod> findByUserId(String userId);
}
